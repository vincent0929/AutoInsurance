package com.vincent.autoinsurance.Context;

/**
 * Created by Vincent on 2016/4/3 0003.
 */
public class MyException extends Exception {

    private static final long serialVersionUID = -88888L;
    public MyException(String detailMessage) {
        super(detailMessage);
    }
}
