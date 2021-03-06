package lcs.fcmtest.utils;

public class Constants {
    public static final String ROLE_PREFERENCE = "role";
    public static final String TOKEN_PREFERENCE = "token";
    public static final String USER_PREFERENCE = "user";
    public static final String PARENT_PREFERENCE = "parent";
    public static final String CHILDRENS_DATABASE = "childrens";
    public static final String PARENTS_DATABASE = "parents";
    public static final String NOTIFICATIONS_DATABASE = "notifications";
    public static final String SENDER_ID = "sender_id";
    public static final String SENDER_NAME = "sender_name";
    public static final String PACKAGE_NAME = "package_name";
    public static final String APP_NAME = "app_name";
    public static final String BODY = "body";
    public static final String TITLE = "title";
    public static final String URL = "https://fcm.googleapis.com/fcm/send";
    public static final String SETUP_CONCLUDED = "setup_complete";

    public static final String NAME_DATA_KEY = "name";
    public static final String EMAIL_DATA_KEY = "email";
    public static final String AUTHORIZED_EMAIL_DATA_KEY = "authorized_email";


    public static String JSON_DATA ="{\n" +
            "    \"data\": {\n" +
            "        \"" + SENDER_ID + "\": \"%s\",\n" +
            "        \"" + SENDER_NAME + "\": \"%s\",\n" +
            "        \"" + PACKAGE_NAME + "\": \"%s\",\n" +
            "        \"" + APP_NAME + "\": \"%s\"\n" +
            "     },\n" +
            "    \"registration_ids\": [\"%s\"]\n" +
            "}";

    public static String JSON_NOTIFICATION ="{\n" +
            "    \"notification\": {\n" +
            "        \"" + BODY + "\": \"%s\",\n" +
            "        \"" + TITLE + "\": \"%s\"\n" +
            "     },\n" +
            "    \"registration_ids\": [\"%s\"]\n" +
            "}";

    public static String JSON_SETUP_CONCLUDED ="{\n" +
            "    \"data\": {\n" +
            "        \"" + SENDER_ID + "\": \"%s\",\n" +
            "        \"" + SETUP_CONCLUDED + "\": \"%s\"\n" +
            "     },\n" +
            "    \"registration_ids\": [\"%s\"]\n" +
            "}";
}
