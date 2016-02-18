package de.desertfox.analyse.whatsapp.core;

import java.util.ArrayList;
import java.util.List;

import wordcloud.WordFrequency;
import wordcloud.nlp.FrequencyAnalyzer;
import wordcloud.nlp.normalize.CharacterStrippingNormalizer;
import wordcloud.nlp.normalize.UpperCaseNormalizer;
import de.desertfox.analyse.whatsapp.core.export.IAnalyser;
import de.desertfox.analyse.whatsapp.model.Message;

public class WordCloudAnalyser implements IAnalyser {

    private List<Message> messages;
    private List<String> words;
    private boolean inputChanged;
    
    public WordCloudAnalyser(List<Message> messages) {
        this.messages = messages;
    }

    private List<String> convertToWordList() {
        List<String> result = new ArrayList<>(messages.size() * 2);
        String text;
        String splitPattern = " ";
        for (Message message : messages) {
            text = message.getText();
            if (text == null) {
                continue;
            }
            String[] words = text.split(splitPattern);
            for (String word : words) {
                if (word == null || word.isEmpty()) {
                    continue;
                }
                if ("omitted".equalsIgnoreCase(word)) {
                    System.out.println();
                }
                result.add(word.toUpperCase());
            }
        }
        return result;
    }
    
    public List<WordFrequency> createFrequencyAnalyzer(List<Message> messages) {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.addNormalizer(new UpperCaseNormalizer());
        frequencyAnalyzer.setWordFrequencesToReturn(750);
        // frequencyAnalyzer.addNormalizer(new UpsideDownNormalizer());
        frequencyAnalyzer.addNormalizer(new CharacterStrippingNormalizer());
        frequencyAnalyzer.setMinWordLength(2);
        if (words == null || inputChanged) {
            words = convertToWordList();
            inputChanged = false;
        }
        List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);
        return wordFrequencies;
    }
    
    @Override
    public Object getResult() {
        return createFrequencyAnalyzer(messages);
    }

    public void setMessages(List<Message> messages) {
        inputChanged = true;
        this.messages = messages;
    }

}
