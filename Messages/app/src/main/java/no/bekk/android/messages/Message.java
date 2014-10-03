package no.bekk.android.messages;

import org.joda.time.DateTime;

import java.util.Comparator;

public class Message implements Comparable<Message> {
    private DateTime date;
    private String from, message, image, _id;

    public Message(String from, String message) {
        this.date = null;
        this.from = from;
        this.message = message;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getId() { return _id; }

    @Override
    public int compareTo(Message message) {
        if (this.date == null && message.date == null) return 0;
        if (this.date == null) {
            return 1;
        }
        if (message.date == null) {
            return -1;
        }
        return message.date.compareTo(this.date);
    }

    public static final Comparator<Message> comparator = new Comparator<Message>() {
        @Override
        public int compare(Message message, Message message2) {
            return message.compareTo(message2);
        }
    };

    public String getDateTimeString() {
        if (this.date == null) return "";
        return date.toString("HH:mm dd.MM.YYYY");
    }
}
