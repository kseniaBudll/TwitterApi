package network;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KseniaB on 12/21/2016.
 */
public class BaseClient {

    private OAuthConsumer consumer;
    private HttpURLConnection request;

    public BaseClient(String key, String secret, String token, String tokenSecret) {
        this.consumer = new DefaultOAuthConsumer(key, secret);
        this.consumer.setTokenWithSecret(token, tokenSecret);
    }

    protected BaseClient get(String url) throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        URL u = new URL(url);
        request = (HttpURLConnection) u.openConnection();
        consumer.sign(request);
        request.connect();
        return this;
    }

    protected BaseClient post(String url) throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        URL u = new URL(url);
        request = (HttpURLConnection) u.openConnection();
        request.setRequestMethod("POST");
        request.setDoOutput(true);
        consumer.sign(request);
        request.connect();
        return this;
    }

    protected int getResponseCode() throws IOException {
        return request.getResponseCode();
    }

    protected String getResponseMessage() throws IOException {
        return String.valueOf(request.getResponseMessage());
    }

    protected String getResponse() {
        try {
            return readJSON(request.getInputStream(), "UTF-8");
        } catch (IOException e) {
            return null;
        }
    }

    private static String readJSON(InputStream inputStream, String encoding) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString(encoding);
    }
}
