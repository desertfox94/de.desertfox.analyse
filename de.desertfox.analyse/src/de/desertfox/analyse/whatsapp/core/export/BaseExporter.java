package de.desertfox.analyse.whatsapp.core.export;

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
