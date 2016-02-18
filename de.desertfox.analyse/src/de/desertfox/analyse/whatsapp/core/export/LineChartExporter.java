/**
 * Filename: LineChartExporter.java
 * Copyright: hsp Handels-Software-Partner GmbH - 2016
 * Purpose: 
 * 
 * Author: Dustin Donges
 * Revision: 
 * Created: 15.02.2016, 16:01:25
 */
package de.desertfox.analyse.whatsapp.core.export;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.LineChartSeries;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import de.desertfox.analyse.whatsapp.model.ChartData;

/**
 * @author d.donges
 *
 */
public class LineChartExporter extends BaseExporter {

    private static final int CHART_MIN_WIDTH = 5;
    private static final int CHART_MAX_WIDTH = 20;

    private ChartData        chartData;

    public LineChartExporter(ChartData chartData) {
        super(null);
        this.chartData = chartData;
    }

    public LineChartExporter(IChartAnalyser chartAnalyser) {
        super(chartAnalyser);
    }

    private int calcChartWidth(int rowCount) {
        int width = (int) (rowCount * 0.7);
        if (width < CHART_MIN_WIDTH) {
            return CHART_MIN_WIDTH;
        } else if (width > CHART_MAX_WIDTH) {
            return CHART_MAX_WIDTH;
        }
        return width;
    }

    @SuppressWarnings("resource")
    @Override
    public IStatus export(File targetDir) {
        finished = false;
        try {

            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("test");

            Cell cell;
            Row row;
            if (chartData == null) {
                chartData = (ChartData) analyser.getResult();
            }
            String[] columnHeaders = chartData.getColumnHeaders();
            row = sheet.createRow(0);
            for (int columnIndex = 0; columnIndex < columnHeaders.length; columnIndex++) {
                cell = row.createCell(columnIndex);
                cell.setCellValue(columnHeaders[columnIndex]);
            }

            int rowCount = chartData.getRowCount();
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                Object[] rowData = chartData.getRowData(rowIndex);
                row = sheet.createRow(rowIndex + 1);
                int columnIndex = 0;
                for (Object object : rowData) {
                    cell = row.createCell(columnIndex++);
                    if (object instanceof String) {
                        cell.setCellValue((String) object);
                    } else if (object instanceof Integer) {
                        cell.setCellValue((Integer) object);
                    }
                }
            }

            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, columnHeaders.length + 1, 1, calcChartWidth(rowCount), 15);

            Chart chart = drawing.createChart(anchor);
            ChartLegend legend = chart.getOrCreateLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);

            LineChartData data = chart.getChartDataFactory().createLineChartData();

            // Use a category axis for the bottom axis.
            ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
            ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

            ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, rowCount, 0, 0));

            for (int i = 1; i < columnHeaders.length; i++) {
                ChartDataSource<Number> dataSource = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, rowCount, i, i));
                LineChartSeries serie = data.addSeries(xs, dataSource);
                serie.setTitle(columnHeaders[i]);
            }
            chart.plot(data, bottomAxis, leftAxis);

            // Write the output to a file
            FileOutputStream fileOut;
            fileOut = new FileOutputStream(targetDir.getPath() + "\\Graph-" + System.currentTimeMillis() + ".xlsx");
            wb.write(fileOut);
            fileOut.close();
            return Status.OK_STATUS;
        } catch (Exception e) {
            e.printStackTrace();
            return Status.CANCEL_STATUS;
        } finally {
            finished = true;
        }

    }

}
