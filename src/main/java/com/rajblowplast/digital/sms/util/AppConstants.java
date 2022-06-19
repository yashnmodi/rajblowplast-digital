package com.rajblowplast.digital.sms.util;

import java.time.format.DateTimeFormatter;

public class AppConstants {

    //common timestamp format to be maintained across all DB records
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static final String SKU_CATEGORY_BOTTLES = "01";
    public static final String SKU_CATEGORY_CAPS = "02";
}
