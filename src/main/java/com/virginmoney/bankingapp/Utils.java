package com.virginmoney.bankingapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void debugMsgWithObj(String s, Object... o) {
        LOGGER.debug(s, o);
    }

    public static void debugMsg(String s) {
        LOGGER.debug(s);
    }
}
