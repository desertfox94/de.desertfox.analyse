package de.desertfox.analyse.whatsapp.ui;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.desertfox.analyse.whatsapp.core.MessageCounter;
import de.desertfox.analyse.whatsapp.core.MessageParser;
import de.desertfox.analyse.whatsapp.core.analyser.MessageByDateAnalyser;
import de.desertfox.analyse.whatsapp.core.analyser.WordCloudAnalyser;
import de.desertfox.analyse.whatsapp.core.export.ExportRunner;
import de.desertfox.analyse.whatsapp.core.export.ImageCloudExporter;
import de.desertfox.analyse.whatsapp.core.export.LineChartExporter;
import de.desertfox.analyse.whatsapp.core.export.TimeSpreadAnalyser;
import de.desertfox.analyse.whatsapp.core.export.WordCloudExporter;
import de.desertfox.analyse.whatsapp.model.AnalyseMethod;
import de.desertfox.analyse.whatsapp.model.ImageTemplate;
import de.desertfox.analyse.whatsapp.model.Message;

public class WhatsappAnalyserController {

    private File                      sourceFile;
    private File                      targetDir;
    private List<Message>             messages;
    private MessageCounter            counter      = new MessageCounter();
    private MessageParser             parser       = new MessageParser();
    private ExportRunner              exportRunner = new ExportRunner();
    private WhatsappAnalyserAppWindow view;

    public WhatsappAnalyserController(WhatsappAnalyserAppWindow view) {
        this.view = view;
    }

    private boolean performCheckSource(String source) {
        if (source == null || (source = source.trim()).isEmpty()) {
            return false;
        }
        File file = new File(source);
        return file.exists() && file.isFile() && file.canRead();
    }

    private boolean performCheckTarget(String target) {
        if (target == null || (target = target.trim()).isEmpty()) {
            return false;
        }
        File file = new File(target);
        return file.exists() && file.isDirectory() && file.canWrite();
    }

    public boolean analyse(AnalyseMethod method) {
        if (method == null) {
            return false;
        }
        switch (method) {
            case MEDIA_COUNT:
                int mediaCount = counter.countMedia(ensureMessages());
                System.out.println("Der Verlauf enth�lt " + mediaCount + " Bilder");
                return true;
            case ANALYSE_BY_TIME:
                TimeSpreadAnalyser spreadAnalyser = new TimeSpreadAnalyser();
                LineChartExporter lineChartExporter = new LineChartExporter(spreadAnalyser.analyse(ensureMessages()));
                exportRunner.runExport(lineChartExporter, targetDir);
                return true;
            case COUNT_BY_SENDER:
                countBysender();
            case COUNT_MESSAGES:
                countAll();
                return true;
            case IMAGES_CLOUD:
                ImageTemplate imageTemplate = view.getImageTemplate();
                ImageCloudExporter imageExporter = new ImageCloudExporter(new WordCloudAnalyser(ensureMessages()), imageTemplate);
                exportRunner.runExport(imageExporter, targetDir);
                return true;
            case MESSAGE_BY_DATE:
                analyseMessagesByDate();
                return true;
            case WORD_CLOUD:
                WordCloudAnalyser wordCloudAnalyser = new WordCloudAnalyser(ensureMessages());
                WordCloudExporter exporter = new WordCloudExporter(wordCloudAnalyser);
                exportRunner.runExport(exporter, targetDir);
                return true;
            default:
                return false;
        }
    }

    private boolean hasFileChanged(File file, String path) {
        return file == null || !file.getPath().equals(path);
    }

    public boolean init(String source, String target) {
        if (performCheckSource(source) && performCheckTarget(target)) {
            if (hasFileChanged(sourceFile, source)) {
                messages = null;
                sourceFile = new File(source);
            }
            targetDir = new File(target);
            return true;
        }
        return false;
    }
    
    public String[] getImageCloudTemplates() {
        return new String[1];
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
            messages = parser.parse(sourceFile);
        }
        return messages;
    }

    private void analyseMessagesByDate() {
        MessageByDateAnalyser messageByDateAnalyser = new MessageByDateAnalyser(ensureMessages());
        exportRunner.runExport(new LineChartExporter(messageByDateAnalyser), targetDir);
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
        System.out.println("L�ngste Nachricht:");
        System.out.println(longest);
    }
}
