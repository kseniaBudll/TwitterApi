package helper;

import java.util.ResourceBundle;

/**
 * Class contains credentials.
 * Created by KseniaB on 12/21/2016.
 */
public class TwitterCred {

    public static final String KEY = ResourceBundle.getBundle("twitter").getString("KEY");
    public static final String SECRET = ResourceBundle.getBundle("twitter").getString("SECRET");
    public static final String TOKEN = ResourceBundle.getBundle("twitter").getString("TOKEN");
    public static final String TOKEN_SECRET = ResourceBundle.getBundle("twitter").getString("TOKEN_SECRET");
    public static final String USER_NAME = ResourceBundle.getBundle("twitter").getString("USER_NAME");
}
