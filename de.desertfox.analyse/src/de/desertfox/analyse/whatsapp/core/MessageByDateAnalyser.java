package de.desertfox.analyse.whatsapp.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.desertfox.analyse.whatsapp.core.export.IChartAnalyser;
import de.desertfox.analyse.whatsapp.model.ChartData;
import de.desertfox.analyse.whatsapp.model.Message;

public class MessageByDateAnalyser implements IChartAnalyser {

	private List<Message> messages;
	private ChartData chartData;

	public MessageByDateAnalyser(List<Message> messages) {
		this.messages = messages;
	}

	public Map<String, Integer> analyseMessagesByDate(Date start, Date end) {
		return analyseMessagesByDate(start, end, false);
	}

	private void fillMapWithDates(Map<String, Integer> dateToMessages, Date start, Date end) {
		if (start == null) {
			start = messages.get(0).getDate();
		}
		if (end == null) {
			end = messages.get(messages.size() - 1).getDate();
		}
		Calendar calCurrent = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		calCurrent.setTime(start);
		calEnd.setTime(end);

		while (calCurrent.compareTo(calEnd) < 1) {
			dateToMessages.put(Message.dateFormat.format(calCurrent.getTime()), 0);
			calCurrent.set(Calendar.DAY_OF_YEAR, calCurrent.get(Calendar.DAY_OF_YEAR) + 1);
		}
	}

	public Map<String, Integer> analyseMessagesByDate(Date start, Date end, boolean addDaysWithoutMessages) {
		Map<String, Integer> dateToMessages = new HashMap<String, Integer>();
		Calendar calendar = Calendar.getInstance();
		Calendar calStart = null;
		if (start != null) {
			calStart = Calendar.getInstance();
			calStart.setTime(start);
		}
		Calendar calEnd = null;
		if (end != null) {
			calEnd = Calendar.getInstance();
			calEnd.setTime(end);
		}

		if (addDaysWithoutMessages) {
			fillMapWithDates(dateToMessages, start, end);
		}

		for (Message message : messages) {
			calendar.setTime(message.getDate());
			if (calStart != null && calendar.compareTo(calStart) < 0) {
				continue;
			} else if (calEnd != null && calendar.compareTo(calEnd) > 0) {
				continue;
			}
			String dateFormatString = Message.dateFormat.format(message.getDate());
			Integer count = dateToMessages.get(dateFormatString);
			if (count == null) {
				count = 0;
			}
			count++;
			dateToMessages.put(dateFormatString, count);
		}
		return dateToMessages;
	}

	public ChartData createChartData(Map<String, Integer> dateToMessages) {
		List<String> dates = new ArrayList<String>(dateToMessages.keySet());
		Collections.sort(dates, new Comparator<String>() {

			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();

			@Override
			public int compare(String o1, String o2) {
				try {
					cal1.setTime(Message.dateFormat.parse(o1));
					cal2.setTime(Message.dateFormat.parse(o2));
					return cal1.compareTo(cal2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		String[] orderedDates = new String[dates.size()];
		dates.toArray(orderedDates);
		chartData = new DateToMessageChartData(orderedDates, dateToMessages);
		return chartData;
	}

	public ChartData createChartData() {
		return createChartData(analyseMessagesByDate(null, null));
	}

	public ChartData createChartData(Date start, Date end) {
		return createChartData(analyseMessagesByDate(start, end));
	}

	private class DateToMessageChartData implements ChartData {

		private String[] columnHeaders = new String[] { "Tag", "Anzahl Nachrichten" };
		private Map<String, Integer> dateToMessages;
		private String[] orderedDates;

		public DateToMessageChartData(String[] orderedDates, Map<String, Integer> dateToMessages) {
			this.dateToMessages = dateToMessages;
			this.orderedDates = orderedDates;
		}

		@Override
		public String[] getColumnHeaders() {
			return columnHeaders;
		}

		@Override
		public Object[] getRowData(int rowIndex) {
			String dateString = orderedDates[rowIndex];
			return new Object[] { dateString, dateToMessages.get(dateString) };
		}

		@Override
		public int getRowCount() {
			return orderedDates.length;
		}

		@Override
		public int getColumnCount() {
			return columnHeaders.length;
		}

	}

	@Override
	public ChartData getResult() {
		if (chartData == null) {
			createChartData();
		}
		return chartData;
	}

}
