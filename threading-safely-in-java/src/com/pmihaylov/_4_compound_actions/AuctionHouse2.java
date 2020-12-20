package com.pmihaylov._4_compound_actions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuctionHouse2 {
    private Map<String, Integer> productsByBids = new ConcurrentHashMap<>();

    public boolean bidFor(String product, int price) {
        return productsByBids.putIfAbsent(product, price) == null;
    }

    public int getBid(String product) {
        return productsByBids.get(product);
    }
}
