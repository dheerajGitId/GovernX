package com.clientdata.dashboardservice.configuration;

public class TenantContext {
    private static final ThreadLocal<String> tenant = new ThreadLocal<>();

    public static String getTenant() {
        return tenant.get();
    }

}
