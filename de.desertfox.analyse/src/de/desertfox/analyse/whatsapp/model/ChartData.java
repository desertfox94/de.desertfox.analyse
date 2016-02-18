package de.desertfox.analyse.whatsapp.model;

public interface ChartData {

    public String[] getColumnHeaders();
    
    public Object[] getRowData(int rowIndex);
    
    public int getRowCount();
    
    public int getColumnCount();
    
}
