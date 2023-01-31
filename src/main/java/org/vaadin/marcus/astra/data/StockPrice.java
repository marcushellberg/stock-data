package org.vaadin.marcus.astra.data;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("stock_price")
public class StockPrice {

    @PrimaryKeyColumn(name = "symbol", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String symbol;
    @PrimaryKeyColumn(name = "time", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String time;
    private double open;
    private double high;
    private double low;
    private double close;
    private double adjustedclose;
    private long volume;
    private double dividendamount;
    private double splitcoefficient;

    public StockPrice() {}

    public StockPrice(String symbol, StockUnit unit) {
        if (symbol != null && unit != null) {
            this.symbol = symbol;
            this.time = unit.getDate();
            this.open = unit.getOpen();
            this.high = unit.getHigh();
            this.low = unit.getLow();
            this.close = unit.getClose();
            this.adjustedclose = unit.getAdjustedClose();
            this.volume = unit.getVolume();
            this.dividendamount = unit.getDividendAmount();
            this.splitcoefficient = unit.getSplitCoefficient();
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getAdjustedclose() {
        return adjustedclose;
    }

    public void setAdjustedclose(double adjustedclose) {
        this.adjustedclose = adjustedclose;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getDividendamount() {
        return dividendamount;
    }

    public void setDividendamount(double dividendamount) {
        this.dividendamount = dividendamount;
    }

    public double getSplitcoefficient() {
        return splitcoefficient;
    }

    public void setSplitcoefficient(double splitcoefficient) {
        this.splitcoefficient = splitcoefficient;
    }
}