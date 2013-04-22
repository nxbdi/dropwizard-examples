package com.github.bjpbakker.dropwizardexamples.filters;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpRequest;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.EnumSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CorsHeadersFilterTest {
    private static final String HEADER_NOT_SET = null;
    private static final HttpHost HTTP_HOST = new HttpHost("localhost", 59876);

    private Server server;
    private CorsHeadersFilter subject;

    @Before
    public void setUp() throws Exception {
        subject = new CorsHeadersFilter();
        server = bootstrapServerWithFilter(subject);
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void it_adds_CORS_allow_origin_header_on_GET_response() throws Exception {
        HttpResponse response = get("/");
        assertThat(responseHeader("Access-Control-Allow-Origin", response), hasValue("*"));
    }

    @Test
    public void it_leaves_CORS_allow_headers_header_unset_on_GET_response() throws Exception {
        HttpResponse response = get("/");
        assertThat(responseHeader("Access-Control-Allow-Headers", response), is(unset()));
    }

    @Test
    public void it_adds_CORS_allow_origin_header_on_OPTIONS_response() throws Exception {
        HttpResponse response = options("/");
        assertThat(responseHeader("Access-Control-Allow-Origin", response), hasValue("*"));
    }

    @Test
    public void it_adds_CORS_allow_headers_header_on_OPTIONS_response() throws Exception {
        HttpResponse response = options("/");
        assertThat(responseHeader("Access-Control-Allow-Headers", response), hasValue("Content-Type"));
    }

    private HttpResponse get(String resource) throws IOException {
        return execute("GET", resource);
    }

    private HttpResponse options(String resource) throws IOException {
        return execute("OPTIONS", resource);
    }

    private HttpResponse execute(String method, String resource) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpRequest request = new BasicHttpRequest(method, resource);
        return client.execute(HTTP_HOST, request);
    }

    private String responseHeader(String name, HttpResponse response) {
        Header header = response.getFirstHeader(name);
        return header != null ? header.getValue() : HEADER_NOT_SET;
    }

    private Matcher<? super Object> hasValue(Object value) {
        return equalTo(value);
    }

    private Matcher<String> unset() {
        return equalTo(HEADER_NOT_SET);
    }

    private Server bootstrapServerWithFilter(CorsHeadersFilter filter) {
        Server server = new Server(new InetSocketAddress(HTTP_HOST.getHostName(), HTTP_HOST.getPort()));
        ServletContextHandler root = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        root.addFilter(new FilterHolder(filter), "/*", EnumSet.of(DispatcherType.REQUEST));
        root.addServlet(new ServletHolder(new EmptyResourceServlet()), "/*");
        return server;
    }

    private static class EmptyResourceServlet extends GenericServlet {
        @Override
        public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {}
    }
}
