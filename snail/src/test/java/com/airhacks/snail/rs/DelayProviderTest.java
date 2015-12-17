package com.airhacks.snail.rs;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class DelayProviderTest {

    @Test
    public void convertNonNumber() {
        long result = DelayProvider.convert("adf");
        assertThat(result, is(0l));
    }

    @Test
    public void convertValidNumber() {
        long result = DelayProvider.convert("42");
        assertThat(result, is(42l));
    }

}
