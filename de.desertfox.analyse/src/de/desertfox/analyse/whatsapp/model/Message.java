package de.desertfox.analyse.whatsapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    private Date   date;
    private String sender;
    private String text;
    private boolean media;

    public boolean isMedia() {
		return media;
	}

	public void setMedia(boolean media) {
		this.media = media;
	}

	public Message(Date date, String sender, String text) {
        this.date = date;
        this.sender = sender;
        this.text = text;
    }

    public Message(Date date, String sender) {
        super();
        this.date = date;
        this.sender = sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return sender + "\n" + dateTimeFormat.format(date) + "\n" + text;
    }

}
