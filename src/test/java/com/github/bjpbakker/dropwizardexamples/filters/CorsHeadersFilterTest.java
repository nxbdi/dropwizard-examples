package com.github.bjpbakker.dropwizardexamples.filters;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Tests the {@link CorsHeadersFilter} class.
 */
public class CorsHeadersFilterTest {

    private MockHttpServletRequest httpRequest = new MockHttpServletRequest();
    private MockHttpServletResponse httpResponse = new MockHttpServletResponse();
    private MockFilterChain filterChain = new MockFilterChain();

    @Test
    public void itShouldAddCorsHeaderInGetRequest() throws ServletException, IOException {
        CorsHeadersFilter filter = new CorsHeadersFilter();
        filter.init(new MockFilterConfig());
        httpRequest.setMethod("GET");
        filter.doFilter(httpRequest, httpResponse, filterChain);
        // expect header =  Access-Control-Allow-Origin:*
        Assert.assertSame("Don't expect changed response object", httpResponse, filterChain.getResponse());
        Assert.assertEquals("response header Access-Control-Allow-Origin for CORS is not set!", "*" , httpResponse.getHeaderValue("Access-Control-Allow-Origin"));
        Assert.assertNull("response header Access-Control-Allow-Headers for CORS should not be set!", httpResponse.getHeaderValue("Access-Control-Allow-Headers"));
    }


    @Test
    public void itShouldAddCorsHeadersInOptionRequest() throws ServletException, IOException {
        CorsHeadersFilter filter = new CorsHeadersFilter();
        filter.init(new MockFilterConfig());
        httpRequest.setMethod("OPTIONS");
        filter.doFilter(httpRequest, httpResponse, filterChain);
        // expect header =  Access-Control-Allow-Origin:*
        // expect header =  Access-Control-Allow-Headers:Content-Type
        Assert.assertSame("Don't expect changed response object", httpResponse, filterChain.getResponse());
        Assert.assertEquals("response header Access-Control-Allow-Origin for CORS is not set!", "*" , httpResponse.getHeaderValue("Access-Control-Allow-Origin"));
        Assert.assertEquals("response header Access-Control-Allow-Headers for CORS is not set!", "Content-Type" , httpResponse.getHeaderValue("Access-Control-Allow-Headers"));
    }
}
