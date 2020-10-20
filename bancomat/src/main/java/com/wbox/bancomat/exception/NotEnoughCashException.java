package com.wbox.bancomat.exception;

public class NotEnoughCashException extends Exception {
    public NotEnoughCashException(){
        super("Not possible to deliver the requested amount");
    }
}
