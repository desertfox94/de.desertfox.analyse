package de.desertfox.analyse.whatsapp.core.export;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.desertfox.analyse.whatsapp.model.ChartData;
import de.desertfox.analyse.whatsapp.model.Message;
import de.desertfox.analyse.whatsapp.model.TimeSpreadChart;

public class TimeSpreadAnalyser {

	public ChartData analyse(List<Message> messages) {
		Map<Integer, Integer> timeToMessages = new HashMap<Integer, Integer>();
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < 24; i++) {
			timeToMessages.put(i, 0);
		}
		for (Message message : messages) {
			calendar.setTime(message.getDate());
			int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
			Integer count = timeToMessages.get(hourOfDay);
			timeToMessages.put(hourOfDay, ++count);
		}
		return new TimeSpreadChart(timeToMessages);
	
	}
	
}
