package de.desertfox.analyse.whatsapp.core.export;

import java.io.File;

import org.eclipse.core.runtime.IStatus;

public interface IExporter {

	public IStatus export(File targetDir);

	public boolean isFinished();
	
}
