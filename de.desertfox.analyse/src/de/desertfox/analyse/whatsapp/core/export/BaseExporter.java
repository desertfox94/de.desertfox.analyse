package de.desertfox.analyse.whatsapp.core.export;

import de.desertfox.analyse.whatsapp.core.analyser.IAnalyser;

public abstract class BaseExporter implements IExporter {

    protected IAnalyser analyser;
    protected boolean   finished;

    public BaseExporter(IAnalyser analyser) {
        super();
        this.analyser = analyser;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

}
