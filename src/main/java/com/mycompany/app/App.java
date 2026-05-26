package com.mycompany.app;

/**
 * Simple DevOps Build Info application for Jenkins CI portfolio demos.
 */
public class App {

    private static final String APP_NAME = "Jenkins CI/CD App";
    private static final String STATUS = "Running pipelines successfully";
    private static final String BUILD_TOOL = "Maven";
    private static final String DEFAULT_ENVIRONMENT = "local";

    public App() {
    }

    public static void main(String[] args) {
        App app = new App();

        System.out.println("Application: " + app.getAppName());
        System.out.println("Status: " + app.getStatus());
        System.out.println("Environment: " + app.getEnvironment());
        System.out.println("Java Version: " + app.getJavaVersion());
        System.out.println("Build Tool: " + BUILD_TOOL);
    }

    public String getAppName() {
        return APP_NAME;
    }

    public String getStatus() {
        return STATUS;
    }

    public String getEnvironment() {
        String environment = System.getenv("APP_ENV");
        if (environment == null || environment.isBlank()) {
            return DEFAULT_ENVIRONMENT;
        }
        return environment;
    }

    public String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public String getBuildInfo() {
        return "Java " + getJavaVersion() + " | " + BUILD_TOOL + " build";
    }
}
