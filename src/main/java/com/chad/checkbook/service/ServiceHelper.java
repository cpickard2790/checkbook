package com.chad.checkbook.service;

import java.text.DecimalFormat;

public class ServiceHelper {

    public static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
}
