package de.desertfox.analyse.whatsapp.model;

public enum AnalyseMethod {

	COUNT_BY_SENDER("Nachrichten pro Person"),
	ANALYSE_BY_TIME("Zeitliche Verteilung"),
	WORD_CLOUD("WordCloud"),
	MESSAGE_BY_DATE("Nachrichten pro Tag"),
	COUNT_MESSAGES("Nachrichten z�hlen");

	private String displayName;

	private AnalyseMethod(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

}
