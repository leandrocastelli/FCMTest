package lcs.fcmtest.model;

import android.graphics.drawable.Drawable;


public class InfoAboutInstalledApps {
    private String appname;
    private String pname;
    private String versionName;
    private int versionCode;
    private String status = "blocked";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public InfoAboutInstalledApps(String appname, String pname, String versionName, int versionCode) {
        this.appname = appname;
        this.pname = pname;
        this.versionName = versionName;
        this.versionCode = versionCode;
    }

    public InfoAboutInstalledApps() {}

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }



    @Override
    public String toString() {
        return "InfoAboutInstalledApps{" +
                "appname='" + appname + '\'' +
                ", pname='" + pname + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", status=" + status +
                '}';
    }
}
