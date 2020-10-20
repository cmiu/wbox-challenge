package com.wbox.bancomat.model;

public enum BanknoteType {
    ONE_RON(1),
    FIVE_RON(5),
    TEN_RON(10),
    FIFTY_RON(50),
    HUNDRED_RON(100);

    private Integer value;

    public Integer getValue() {
        return value;
    }

    BanknoteType(Integer value) {
        this.value = value;
    }
}
