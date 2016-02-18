package de.desertfox.analyse.whatsapp.model;

import java.util.Map;

public class TimeSpreadChart implements ChartData {

    private String[] columnHeaders = new String[] {"Zeit", "Anzahl Nachrichten" };
    private Map<Integer, Integer> timeToMessageMap;

    public TimeSpreadChart(Map<Integer, Integer> timeToMessageMap) {
        this.timeToMessageMap = timeToMessageMap;
    }

    @Override
    public int getColumnCount() {
        return columnHeaders.length;
    }
    
    @Override
    public String[] getColumnHeaders() {
        return columnHeaders;
    }

    @Override
    public Object[] getRowData(int rowIndex) {
        return new Object[] {
                rowIndex + ":00 - " + rowIndex + ":59",
                timeToMessageMap.get(rowIndex)
        };
    }

    @Override
    public int getRowCount() {
        return timeToMessageMap.size();
    }

}
