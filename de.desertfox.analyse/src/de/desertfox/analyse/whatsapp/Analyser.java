package de.desertfox.analyse.whatsapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.desertfox.analyse.whatsapp.model.Message;
import de.desertfox.analyse.whatsapp.model.TimeSpreadChart;

public class Analyser {

    private File           file;
    private List<Message>  messages;
    private MessageCounter counter = new MessageCounter();
    private MessageParser  parser = new MessageParser();
    private LineChartExporter chartExporter = new LineChartExporter();

    public Analyser(String path) {
        this.file = new File(path);
    }

    public static void main(String[] args) {
        Analyser analyser = new Analyser("C:\\users\\d.donges\\Desktop\\WhatsApp Chat with RFSP Treffen 14.02 14 Uhr.txt");
        analyser.completeAnalysis();
    }

    public void completeAnalysis() {
        System.out.println("Chat Analyse für: ");
        System.out.println(file.getName());
        ensureMessages();
        countBysender();
        findLongestMessage();
        countAll();
        TimeSpreadChart timeSpreadChart = analyseTimeSpread();
        chartExporter.export(timeSpreadChart);
    }

    private void printPlacholderLine() {
        System.out.println("- - - - - - - - - - - - - - - -");
    }
    
    public void countAll() {
        printPlacholderLine();
        System.out.println("Anzahl Nachrichten: " + ensureMessages().size());
    }

    public void countBysender() {
        printPlacholderLine();
        Map<String, Integer> senderMessageCount = counter.countBySender(ensureMessages());
        Set<Entry<String, Integer>> entrySet = senderMessageCount.entrySet();
        System.out.println("Name\t\tNachrichten");
        for (Entry<String, Integer> entry : entrySet) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    }

    private List<Message> ensureMessages() {
        if (messages == null) {
            messages = parser.parse(file);
        }
        return messages;
    }
    
    private TimeSpreadChart analyseTimeSpread() {
        printPlacholderLine();
        System.out.println("Zeitliche Verteilung:\n");
        Map<Integer, Integer> timeToMessages = new HashMap<Integer, Integer>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 24; i++) {
            timeToMessages.put(i, 0);
        }
        for (Message message : ensureMessages()) {
            calendar.setTime(message.getDate());
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            Integer count = timeToMessages.get(hourOfDay);
            timeToMessages.put(hourOfDay, ++count);
        }
        TimeSpreadChart timeSpreadChart = new TimeSpreadChart(timeToMessages);
        List<Integer> hours = new ArrayList<Integer>(timeToMessages.keySet());
        Collections.sort(hours);
        for (Integer hour : hours) {
            Integer messageCount = timeToMessages.get(hour);
            System.out.println(hour + ":00 - " + hour +":59\t" + messageCount);
        }
        return timeSpreadChart;
    }
    
    public void findLongestMessage() {
        Message longest = null;
        for (Message message : ensureMessages()) {
            if (longest == null) {
                if (message.getText() != null) {
                    longest = message;
                }
                continue;
            }
            if (message.getText() != null && message.getText().length() > longest.getText().length()) {
                longest = message;
            }
        }
        if (longest == null) {
            return;
        }
        printPlacholderLine();
        System.out.println("Längste Nachricht:");
        System.out.println(longest);
    }
}
