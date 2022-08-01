package com.safina.lyudmila.tests.testcontainers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DemoTest {

    @Test
    public void successTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failTest() {
        Assertions.assertTrue(false);
    }

    @Disabled
    @Test
    public void disabledTest() {
        Assertions.assertTrue(false);
    }
}
