package com.mycompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the DevOps Build Info application.
 */
public class AppTest {

    @Test
    public void testAppName() {
        App app = new App();
        assertEquals("Jenkins CI/CD App", app.getAppName());
    }

    @Test
    public void testStatus() {
        App app = new App();
        assertEquals("Running pipelines successfully", app.getStatus());
    }

    @Test
    public void testDefaultEnvironment() {
        App app = new App();
        String environment = System.getenv("APP_ENV");
        if (environment == null || environment.isBlank()) {
            assertEquals("local", app.getEnvironment());
        } else {
            assertEquals(environment, app.getEnvironment());
        }
    }

    @Test
    public void testBuildInfoContainsMavenOrJavaInfo() {
        App app = new App();
        String buildInfo = app.getBuildInfo().toLowerCase();

        assertTrue(
                buildInfo.contains("maven") || buildInfo.contains("java"),
                "Build info should mention Maven or Java");
    }
}
