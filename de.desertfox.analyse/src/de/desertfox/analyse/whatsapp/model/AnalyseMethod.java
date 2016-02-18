package de.desertfox.analyse.whatsapp.model;

public enum AnalyseMethod implements IComboViewableEnum {

	COUNT_BY_SENDER("Nachrichten pro Person"),
	ANALYSE_BY_TIME("Zeitliche Verteilung"),
	MEDIA_COUNT("Bilder zählen"),
	WORD_CLOUD("WordCloud"),
	IMAGES_CLOUD("WordCloud aus Bild"),
	MESSAGE_BY_DATE("Nachrichten pro Tag"),
	COUNT_MESSAGES("Nachrichten zählen");

	private String displayName;

	private AnalyseMethod(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

    @Override
    public String getId() {
        return null;
    }

}
