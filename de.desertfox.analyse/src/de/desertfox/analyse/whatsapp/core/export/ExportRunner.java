package de.desertfox.analyse.whatsapp.core.export;

import java.io.File;

public class ExportRunner {

	private IExporter exporter;
	private File targetDir;

	public boolean isFinished() {
		if (exporter == null) {
			return true;
		}
		return exporter.isFinished();
	}

	public void runExport(IExporter exporter, File targetDir) {
		if (!isFinished()) {
			return;
		}
		this.exporter = exporter;
		this.targetDir = targetDir;
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				ExportRunner.this.exporter.export(ExportRunner.this.targetDir);
			}
		};
		new Thread(runnable).start();
	}
}
