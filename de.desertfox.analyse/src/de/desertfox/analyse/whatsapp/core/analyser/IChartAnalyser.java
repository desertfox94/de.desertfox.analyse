package de.desertfox.analyse.whatsapp.core.analyser;

import de.desertfox.analyse.whatsapp.model.ChartData;

public interface IChartAnalyser extends IAnalyser {

	@Override
	public ChartData getResult();
	
}
