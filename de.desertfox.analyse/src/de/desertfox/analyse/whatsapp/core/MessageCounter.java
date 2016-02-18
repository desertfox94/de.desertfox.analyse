package de.desertfox.analyse.whatsapp.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.desertfox.analyse.whatsapp.model.Message;

public class MessageCounter {

    public Map<String, Integer> countBySender(List<Message> messages) {
        Map<String, Integer> senderMessageCount = new HashMap<String, Integer>();
        for (Message message : messages) {
            String sender = message.getSender();
            Integer messageCount = senderMessageCount.get(sender);
            if (messageCount == null) {
                messageCount = 0;
            }
            senderMessageCount.put(sender, ++messageCount);
        }
        return senderMessageCount;
    }
    
    public int countMedia(List<Message> messages) {
        int mediaCount = 0;
        for (Message message : messages) {
            if (message.isMedia()) {
                mediaCount++;
            }
        }
        return mediaCount;
    }
    
}
