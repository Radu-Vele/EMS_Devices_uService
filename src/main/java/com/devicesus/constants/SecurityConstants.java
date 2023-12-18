package com.devicesus.constants;

public class SecurityConstants {
    public static final String[] ALLOWED_ORIGINS = {
            "http://localhost:3000",
            "http://localhost:5173",
            "http://172.20.0.6:80",
            "http://localhost:80",
            "http://localhost",
            "http://172.20.0.3:8080",
            "http://172.20.0.3:8081"
    };
    public static final String[] NO_AUTH_REQUIRED_PATTERNS = {"/users/registerUserId"};
    public static final String[] ADMIN_REQUIRED_PATTERNS = {"/devices/create", "/devices/deleteById", "/devices/edit", "/devices/getDetailsById","/devices/getAll", "/users/removeUserAndMapping", "/users/addDeviceToUser", "/users/removeDeviceFromUser"
    };
    public static final String[] USER_AUTH_REQUIRED_PATTERNS = {"/users/getAllDevices"};

}
