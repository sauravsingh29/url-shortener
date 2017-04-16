package com.ss.url.cache;

import com.ss.url.entity.Account;
import org.springframework.stereotype.Component;

/**
 * Created by Saurav on 14-04-2017.
 */
@Component
public class AccountCache extends AppCache<Account> {

    /**
     * Only used on internal purpose
     * @return
     */
    public String openedAccount() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<html>").append("<body>");
        buffer.append("<h1>").append("Account Information").append("</h1>");
        buffer.append("<br>").append("</br>");
        for (Account account : cache.values()){
            buffer.append("<br> Account Id :: ").append(account.getAccountId()).append("\t Password :: ").append(account.getPassword());
        }
        buffer.append("</body>").append("</html>");
        return buffer.toString();
    }
}
