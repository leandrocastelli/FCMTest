package lcs.fcmtest.model;

public class App {
    private String appName;
    private String packageName;
    private String status;
    public App(String appName, String packageName, String status) {
        this.appName = appName;
        this.packageName = packageName;
        this.status = status;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
