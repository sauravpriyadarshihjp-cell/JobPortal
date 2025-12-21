package com.Party.PartyService.PartyException;

public class PrtException extends Exception{
    private PrtTransactionMessage objExceptionmsg;
    public PrtException(PrtTransactionMessage message){
        this.objExceptionmsg = message;
    }
}
