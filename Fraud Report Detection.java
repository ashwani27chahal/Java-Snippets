package com.signifyd.challenge;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Solution {
    public static Map<String, Map<Date, String>> customerTransaction = new HashMap<>();
    public static void main(String[] args) throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        summary(dateFormat.parse("2015-01-01"), "joe@signifyd.com", "PURCHASE");
        summary(dateFormat.parse("2015-01-01"), "fraudster@fraud.com", "FRAUD_REPORT");
        summary(dateFormat.parse("2015-02-03"), "fraudster@fraud.com", "FRAUD_REPORT");
        summary(dateFormat.parse("2015-02-10"), "joe@signifyd.com", "PURCHASE");
        summary(dateFormat.parse("2015-02-14"), "fraudster@fraud.com", "PURCHASE");
        summary(dateFormat.parse("2015-03-15"), "joe@signifyd.com", "PURCHASE");
        summary(dateFormat.parse("2015-05-01"), "joe@signifyd.com", "PURCHASE");
        summary(dateFormat.parse("2015-10-01"), "joe@signifyd.com", "PURCHASE");


    }

    public static void summary(Date date, String customer, String event) {

        Map<Date, String> eventHistory = new HashMap<>();

        if (event == "FRAUD_REPORT") {
            if (customerTransaction.containsKey(customer))
                eventHistory = customerTransaction.get(customer);
            eventHistory.put(date, event);
            customerTransaction.put(customer, eventHistory);
        }


        else {
            int fraudCount = 0;
            int goodCount = 0;
            int unconfirmedCount = 0;
            int noHistoryCount =0;
            String eventSummary = "";



            // ********** NO_HISTORY ***************
            if (!customerTransaction.containsKey(customer)) {
                eventSummary = "NO_HISTORY";
                print(date, customer, eventSummary, noHistoryCount);
            }


            else {
                Map<Date, String> previousHistory = customerTransaction.get(customer);

                // ********** FRAUD_HISTORY ***************
                for (Date key : previousHistory.keySet()) {
                    if (previousHistory.get(key) == "FRAUD_REPORT") {
                        eventSummary = "FRAUD_HISTORY";
                        fraudCount++;
                    }
                }


                if (fraudCount == 0) {

                    // ********** GOOD_HISTORY ***************
                    for (Date key : previousHistory.keySet()) {
                        long time = (date.getTime() - key.getTime());
                        int days = (int) (time / (3600 * 24*1000));


                        if (days > 90 && previousHistory.get(key) == "PURCHASE") {
                            eventSummary = "GOOD_HISTORY";
                            goodCount++;
                        }
                    }

                    // ********** UNCONFIRMED_HISTORY ***************
                    if (goodCount == 0) {
                        for (Date key : previousHistory.keySet()) {
                            long time = (date.getTime() - key.getTime());
                            int days = (int) (time / (3600 * 24*1000));
                            if (days <= 90 && previousHistory.get(key) == "PURCHASE") {
                                eventSummary = "UNCONFIRMED_HISTORY";
                                unconfirmedCount++;
                            }
                        }
                        print(date, customer, eventSummary, unconfirmedCount);
                    }
                    else
                        print(date, customer, eventSummary, goodCount);

                }
                else
                    print(date, customer, eventSummary, fraudCount);



            }
            if (customerTransaction.containsKey(customer))
                eventHistory = customerTransaction.get(customer);
            eventHistory.put(date, event);
            customerTransaction.put(customer, eventHistory);

        }



    }

    public static void print(Date date, String customer, String eventSummary, int summaryCount) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = dateFormat.format(date);
        if (summaryCount!=0)
            System.out.println(stringDate+","+customer+","+eventSummary+":"+summaryCount);
        else
            System.out.println(stringDate+","+customer+","+eventSummary);

    }
}
