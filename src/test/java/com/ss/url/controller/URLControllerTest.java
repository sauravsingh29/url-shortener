package com.ss.url.controller;

import com.ss.url.entity.URLDetails;
import com.ss.url.exception.UrlException;
import com.ss.url.request.URLRequest;
import com.ss.url.service.URLService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Saurav on 18-04-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class URLControllerTest {

    private static final String url = "http://stackoverflow.com/questions/137212/how-to-solve-performance-problem-with-java-securerandom";
    @Mock
    private URLService urlService;
    @Mock
    private HttpServletRequest httpServletRequest;
    @InjectMocks
    private URLController urlController = new URLController(urlService);
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.urlController).build();
    }

    @Test
    public void testRegisterUrl() throws Exception, UrlException {
        URLRequest request = new URLRequest();
        request.setUrl(url);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/register"));
        when(urlService.registerUrl(anyString(), anyInt(), anyString(), anyString())).thenReturn("http://localhost:8080/xCfxDs");
        this.mockMvc.perform(post("/register").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(" { \"url\" : \"http://stackoverflow.com/questions/137212/how-to-solve-performance-problem-with-java-securerandom\", \"redirectType\" : \"301\"}")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")).andExpect(status().isOk());
    }

    @Test
    public void testRegisterUrlBadRequest() throws Exception, UrlException {
        URLRequest request = new URLRequest();
        request.setUrl(url);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/register"));
        when(urlService.registerUrl(anyString(), anyInt(), anyString(), anyString())).thenReturn("http://localhost:8080/xCfxDs");
        this.mockMvc.perform(post("/register").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(" { \"url\" : \"htft://stackoverflow.com/questions/137212/how-to-solve-performance-problem-with-java-securerandom\", \"redirectType\" : \"301\"}")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    public void testRegisterUrlException() throws Exception, UrlException {
        URLRequest request = new URLRequest();
        request.setUrl(url);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/register"));
        when(urlService.registerUrl(anyString(), anyInt(), anyString(), anyString())).thenThrow(new UrlException("Test", new RuntimeException()));
        this.mockMvc.perform(post("/register").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(" { \"url\" : \"http://stackoverflow.com/questions/137212/how-to-solve-performance-problem-with-java-securerandom\", \"redirectType\" : \"301\"}")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")).andExpect(status().isInternalServerError());

    }


    @Test
    public void testAccountStatics() throws Exception, UrlException {
        Map<String, Integer> out = new HashMap<>(0);
        out.put(url, 10);
        when(urlService.getStatByAccountId("admin")).thenReturn(out);
        this.mockMvc.perform(post("/statistic/admin").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")).andExpect(status().isOk());

    }

    @Test
    public void testAccountStaticsAuth() throws Exception, UrlException {
        Map<String, Integer> out = new HashMap<>(0);
        out.put(url, 10);
        when(urlService.getStatByAccountId("admin")).thenReturn(out);
        this.mockMvc.perform(post("/statistic/Saurav").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")).andExpect(status().isUnauthorized());

    }

    @Test
    public void testAccountStaticsException() throws Exception, UrlException {
        when(urlService.getStatByAccountId("admin")).thenThrow(new UrlException("Dummy-Fail", new RuntimeException("Graceful")));
        this.mockMvc.perform(post("/statistic/admin").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")).andExpect(status().isInternalServerError());

    }

    @Test
    public void testRedirect() throws Exception, UrlException {
        URLDetails urlDetails = new URLDetails();
        urlDetails.setUrl(url);
        urlDetails.setShortUrl("https://localhost:8080/6glfQn");
        urlDetails.setRedirectType(302);
        when(urlService.getUrlDetailsByShortUrl(anyString())).thenReturn(urlDetails);
        doNothing().when(urlService).incrementCounter(anyString());
        this.mockMvc.perform(get("/6glfQn").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")).andExpect(status().is3xxRedirection());
    }

    @Test
    public void testRedirectException() throws Exception, UrlException {
        URLDetails urlDetails = new URLDetails();
        urlDetails.setUrl(url);
        urlDetails.setShortUrl("https://localhost:8080/6glfQn");
        urlDetails.setRedirectType(302);
        when(urlService.getUrlDetailsByShortUrl(anyString())).thenThrow(new UrlException("Url malformed", new URISyntaxException("", "")));
        doNothing().when(urlService).incrementCounter(anyString());
        this.mockMvc.perform(get("/6glfQn").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")).andExpect(status().isInternalServerError());
    }


}
