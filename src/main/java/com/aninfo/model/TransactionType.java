package com.aninfo.model;

public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw");

    private String type;

    TransactionType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

}
