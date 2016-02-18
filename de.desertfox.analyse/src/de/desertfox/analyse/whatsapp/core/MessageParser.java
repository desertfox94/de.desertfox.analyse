package de.desertfox.analyse.whatsapp.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.desertfox.analyse.whatsapp.model.Message;

public class MessageParser {

	public List<Message> parse(File file) {
		List<Message> messages = new ArrayList<Message>();
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy, HH:mm");

			String charset = "UTF-8"; // or what corresponds
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
			String line;

			Message currentMessage = null;

			FilteredStringBuilder messageBuilder = new FilteredStringBuilder();
			while ((line = reader.readLine()) != null) {
				if (!isMessageInfoLine(line)) {
					messageBuilder.append(line);
					messageBuilder.append("\n");
					continue;
				}

				if (currentMessage != null) {
					setText(currentMessage, messageBuilder);
					messages.add(currentMessage);
				}

				int colonIndex = line.indexOf(':', line.indexOf(':') + 1);
				if (colonIndex == -1) {
					if (currentMessage != null) {
						messages.add(currentMessage);
					}
					currentMessage = null;
					continue;
				}
				String messageInfo = line.substring(0, colonIndex);
				String[] messageInfos = messageInfo.split("-");
				String timeStamp = messageInfos[0].trim();
				String sender = messageInfos[1].trim();
				currentMessage = new Message(format.parse(timeStamp), sender);
				messageBuilder = new FilteredStringBuilder(line.substring(colonIndex + 1));
			}
			if (currentMessage != null) {
				setText(currentMessage, messageBuilder);
				messages.add(currentMessage);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messages;
	}

	private void setText(Message message, FilteredStringBuilder builder) {
		String s = builder.toString();
		if (s.trim().toLowerCase().contains("media omitted")) {
		    message.setMedia(true);
			// filter media
			return;
		}
		message.setText(s);
	}
	
	private boolean isMessageInfoLine(String line) {
		int colonIndex = line.indexOf(',');
		if (colonIndex != 10) {
			return false;
		}
		line = line.substring(0, colonIndex);
		return line.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d");
	}

}
