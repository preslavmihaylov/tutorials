package com.pmihaylov._4_compound_actions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuctionHouse1 {
    private Map<String, Integer> productsByBids = new ConcurrentHashMap<>();

    public boolean bidFor(String product, int price) {
        if (!productsByBids.containsKey(product)) {
            productsByBids.put(product, price);
            return true;
        }

        return false;
    }

    public int getBid(String product) {
        return productsByBids.get(product);
    }
}
