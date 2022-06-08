package com.greymatter.sprint.model;

public class WalletHistory {

    private String msg,date,amount,transaction_type;

    public WalletHistory(String msg, String date, String amount, String transaction_type) {
        this.msg = msg;
        this.date = date;
        this.amount = amount;
        this.transaction_type = transaction_type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
}
