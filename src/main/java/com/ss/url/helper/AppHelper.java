package com.ss.url.helper;

import org.apache.tomcat.util.codec.binary.Base64;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Created by Saurav on 14-04-2017.
 */
public final class AppHelper {

    private static final String SHORTENED_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final ThreadLocal<SecureRandom> RANDOM = new ThreadLocal<SecureRandom>() {
        @Override
        protected SecureRandom initialValue() {
            return new SecureRandom();
        }
    };

    /**
     * Avoid creating instance of this class {@link AppHelper}
     */
    private AppHelper() {
    }

    /**
     * Generate a random string out of variable SHORTENED_CHARS.
     *
     * @param len max length of string.
     * @return a shortened url string on provided length.
     */
    public static String getRandomString(final int len) {
        final StringBuffer buffer = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            buffer.append(SHORTENED_CHARS.charAt(RANDOM.get().nextInt(SHORTENED_CHARS.length())));
        }
        return buffer.toString();
    }

    /**
     * Get base url from original url.
     *
     * @param url {@link String}
     * @return baseUrl {@link String}
     * @throws MalformedURLException
     */
    public static String getURLBase(final String url) throws MalformedURLException {
        URL requestURL = null;
        try {
            requestURL = new URL(url);
            String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
            return requestURL.getProtocol() + "://" + requestURL.getHost() + port + "/";
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Invalid url supplied to register");
        }
    }

    /**
     * Decode Base64 encoded Authorization header parameter to get accountId.
     *
     * @param authHeader {@link String}
     * @return accountId {@link String}
     */
    public static String getAccountIdAuthHeader(final String authHeader) {
        String out = null;
        if (null != authHeader) {
            String decoded = new String(Base64.decodeBase64(authHeader.substring(6)), StandardCharsets.UTF_8);
            if (null != decoded) {
                String[] split = decoded.split(":");
                for (String s : split) {
                    out = s;
                    break;
                }
            }
        }
        return out;
    }
}
