package com.greendot.challenge;

import org.assertj.core.data.Offset;
import org.junit.Test;
import static org.assertj.core.api.Java6Assertions.within;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FibonacciTests {
    @Test
    public void testCelsiusToFahrenheitConversion() {
        final Offset<Float> precision = within(0.01f);

    }
}
