package com.greymatter.sprint.utils;

import com.greymatter.sprint.model.Notification;
import com.greymatter.sprint.model.StepHistory;
import com.greymatter.sprint.model.WalletHistory;

import java.util.ArrayList;
import java.util.List;

public class ConstantList {


    public static List<WalletHistory> getWalletList() {
        List<WalletHistory> walletHistoryList = new ArrayList<>();
        walletHistoryList.add(new WalletHistory(
                "Money sent to Bank Account",
                "06 May, 10 : 57 pm",
                "-₹100",
                "Credited"
                ));
        walletHistoryList.add(new WalletHistory(
                "Money sent to Bank Account",
                "07 May, 11 : 57 pm",
                "-₹50",
                "Credited"
                ));
        walletHistoryList.add(new WalletHistory(
                "Money sent to Bank Account",
                "08 May, 11 : 59 pm",
                "-₹30",
                "Credited"
                ));

        return walletHistoryList;
    }

    public static List<StepHistory> getStepList() {
        List<StepHistory> stepHistoryList = new ArrayList<>();
        stepHistoryList.add(new StepHistory(
                "166",
                "150 kcal",
                "30",
                "09 , May"
        ));
        stepHistoryList.add(new StepHistory(
                "170",
                "160 kcal",
                "40",
                "08 , May"
        ));
        stepHistoryList.add(new StepHistory(
                "110",
                "110 kcal",
                "30",
                "07 , May"
        ));
        stepHistoryList.add(new StepHistory(
                "100",
                "80 kcal",
                "20",
                "06 , May"
        ));
        return stepHistoryList;
    }

    public static List<Notification> getNotificationList() {
        List<Notification> notificationList =  new ArrayList<>();
        notificationList.add(new Notification(
                "Amount Transfer",
                "Successfull",
                "05 May, 10 : 57 pm"
        ));
        notificationList.add(new Notification(
                "Amount Transfer",
                "Successfull",
                "05 May, 10 : 57 pm"
        ));
        notificationList.add(new Notification(
                "Amount Transfer",
                "Successfull",
                "05 May, 10 : 57 pm"
        ));
        notificationList.add(new Notification(
                "Amount Transfer",
                "Successfull",
                "05 May, 10 : 57 pm"
        ));
        return notificationList;
    }
}
