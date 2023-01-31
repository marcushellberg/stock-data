package org.vaadin.marcus.astra.data;

import java.util.ArrayList;
import java.util.List;

public record StockSymbol(String symbol, String name, String exchange) {

    // Symbols for testing
    public static List<StockSymbol> supportedSymbols() {
        // For demo purposes we list only few tickers. For full 8000+ ETF and Stock list you can parse results of
        //  APi endpoint https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=demo
        List<StockSymbol> supportedSymbols = new ArrayList<>();
        supportedSymbols.add(new StockSymbol("AAPL", "Apple Inc", "NASDAQ"));
        supportedSymbols.add(new StockSymbol("IBM", "IBM (International Business Machines) Corp", "NYSE"));
        supportedSymbols.add(new StockSymbol("AMZN", "Amazon.com Inc", "NASDAQ"));
        return supportedSymbols;
    }
}
