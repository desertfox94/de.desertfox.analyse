package de.desertfox.analyse.whatsapp.core.export;

public abstract class BaseExporter implements IExporter {

	protected boolean finished;
	
	@Override
	public boolean isFinished() {
		return finished;
	}
	
}
