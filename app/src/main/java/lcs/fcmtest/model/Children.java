package lcs.fcmtest.model;

import java.util.List;

public class Children extends Person{
    private List<Notification> notifications;

    public String getParentEmail() {
        return parent_email;
    }

    public void setParentEmail(String parent_email) {
        this.parent_email = parent_email;
    }

    private String parent_email;

    public Children(String name, String email, String id, String parent_email) {
        super(name, email, id);
        this.parent_email = parent_email;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
