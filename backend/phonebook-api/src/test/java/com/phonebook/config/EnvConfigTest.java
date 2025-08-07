package com.phonebook.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvConfigTest {

    @Test
    void testGetExistingKey() {
        String expectedValue = System.getenv("TEST_ENV_KEY");
        if (expectedValue == null) {
            expectedValue = "some_test_value"; // fallback for local test
            System.out.println("Warning: TEST_ENV_KEY not set in environment; test may be unreliable.");
        }

        String actualValue = EnvConfig.get("TEST_ENV_KEY");

        assertNotNull(actualValue, "EnvConfig.get should return a value for existing key");
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void testGetNonExistingKey() {
        String value = EnvConfig.get("NON_EXISTING_KEY");
        // Dotenv.get returns null if key not found
        assertNull(value, "EnvConfig.get should return null for unknown keys");
    }
}
