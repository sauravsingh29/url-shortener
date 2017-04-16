package com.ss.url.cache;

import com.ss.url.entity.URLDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Saurav on 14-04-2017.
 */
@Component
public class URLCache extends AppCache<URLDetails> {

    /**
     * Get URLDetails {@link URLDetails} object based on shortened url
     *
     * @param shortUrl {@link String}
     * @return urlDetails {@link URLDetails} if found else null
     */
    public URLDetails getUrlDetailsByShortUrl(final String shortUrl) {
        for (String url : cache.keySet()) {
            int index = cache.get(url).getShortUrl().lastIndexOf("/") + 1;
            if (cache.get(url).getShortUrl().substring(index).equals(shortUrl))
                return cache.get(url);
        }
        return null;
    }

    /**
     * Update redirectType for given url.
     *
     * @param url          {@link String}
     * @param redirectType {@link Integer}
     */
    public void updateRedirectType(final String url, final int redirectType) {
        cache.get(url).setRedirectType(redirectType);
    }

    /**
     * Get URLDetails {@link URLDetails} map object based on accountId
     *
     * @param accountId
     * @return
     */
    public Map<String, URLDetails> getUrlDetailsByAccountId(final String accountId) {
        final Map<String, URLDetails> details = new HashMap<>();

        for (String key : cache.keySet())
            if (accountId.equals(cache.get(key).getAccountId()))
                details.put(cache.get(key).getUrl(), cache.get(key));
        return details;
    }

    /**
     * Only used on internal purpose
     *
     * @return
     */
    public String registerUrlInfo() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<h1>").append("URL Registration Information").append("</h1>");
        buffer.append("<br>").append("</br>");
        for (URLDetails urlDetails : cache.values()) {
            buffer.append("<br>").append(urlDetails.toString()).append("</br>");
        }
        buffer.append("</body>").append("</html>");
        return buffer.toString();
    }

}
