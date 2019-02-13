package lcs.fcmtest.model;

public class Notification {
    private String owner;
    private String type;
    private boolean value;
    private boolean answered;

    public Notification(String owner, String type, boolean value, boolean answered) {
        this.owner = owner;
        this.type = type;
        this.value = value;
        this.answered = answered;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }


}
