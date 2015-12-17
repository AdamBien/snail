package com.airhacks;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import com.airhacks.snail.rs.DelayProvider;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class SnailsResourceIT {

    JAXRSClientProvider provider = JAXRSClientProvider.buildWithURI("http://localhost:8080/snail-sample/resources/snails");

    @Test
    public void snails() {
        final String delay = "1000";
        long start = System.currentTimeMillis();
        Response response = provider.
                target().
                request().
                header(DelayProvider.DELAY_HEADER_NAME, delay).
                get();
        assertThat(response.getStatus(), is(200));
        String delayValue = response.getHeaderString(DelayProvider.DELAY_HEADER_NAME);
        long end = (System.currentTimeMillis() - start);
        assertThat(delayValue, is(delay));
        assertTrue(end > Long.valueOf(delayValue));

    }

}
