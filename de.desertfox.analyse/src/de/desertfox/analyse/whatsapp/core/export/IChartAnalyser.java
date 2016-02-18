package de.desertfox.analyse.whatsapp.core.export;

import de.desertfox.analyse.whatsapp.model.ChartData;

public interface IChartAnalyser extends IAnalyser {

	@Override
	public ChartData getResult();
	
}
