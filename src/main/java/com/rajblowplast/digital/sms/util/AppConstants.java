package com.rajblowplast.digital.sms.util;

import java.time.format.DateTimeFormatter;

public class AppConstants {

    //common timestamp format to be maintained across all DB records
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static final String SKU_CATEGORY_BOTTLES = "01";
    public static final String SKU_CATEGORY_CAPS = "02";
    
    //common error-messages
    public static final String SRC="0";
    public static final String ARC="1";
    public static final String ARC_MSG="Authentication required.";
    public static final String FRC="2";
    public static final String SEC="0";
    public static final String FEC="2";
    public static final String SUCCESS="success";
    public static final String I201_MSG="operation successful";
    public static final String I202="I202";
    public static final String I202_MSG="invalid item details";
    public static final String I203="I203";
    public static final String I203_MSG="operation failed";
    public static final String I204="I204";
    public static final String I204_MSG="item not found";
    public static final String I205="I205";
    public static final String I205_MSG="item already present";
    public static final String E401="E401";
    public static final String E401_MSG="Oops! You have entered invalid username/password.";
    public static final String E402="E402";
    public static final String E402_MSG="User already exists!";
    public static final String E404="E404";
    public static final String E404_MSG="page not found.";
    public static final String I999="I999";
    public static final String I999_MSG="Sorry! Something went wrong. Please try again.";

}
