package org.vaadin.marcus.astra.service;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Component;
import org.vaadin.marcus.astra.data.StockPrice;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Collections;

@Component
public class StockPriceProducer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PulsarTemplate<StockPrice> pulsarTemplate;

    @Autowired
    public StockPriceProducer(PulsarTemplate<StockPrice> pulsarTemplate) {
        this.pulsarTemplate = pulsarTemplate;
    }

    public void produceStockPriceData(final String symbol) {

        AlphaVantage.api()
                .timeSeries()
                .intraday()
                .forSymbol(symbol)
                .interval(Interval.ONE_MIN)
                .outputSize(OutputSize.COMPACT)
                .onSuccess(e -> handleSuccess(symbol, (TimeSeriesResponse) e))
                .onFailure(this::handleFailure)
                .fetch();
    }

    public void handleSuccess(String symbol, TimeSeriesResponse response) {

        var stockUnits = response.getStockUnits();
        // Reverse StockUnits to list the oldest items first
        Collections.reverse(stockUnits);
        var stockPrices = stockUnits.stream().map(unit -> new StockPrice(symbol, unit));

        // Publish items to pulsar with 100ms intervals
        Flux.fromStream(stockPrices)
                // Delay elements for demo chart, don't do this in real life
                .delayElements(Duration.ofMillis(100))
                .subscribe(stockPrice -> {
                    try {
                        pulsarTemplate.sendAsync(stockPrice);
                    } catch (PulsarClientException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void handleFailure(AlphaVantageException error) {
        /* uh-oh! */
        logger.info("handleFailure: AlphaVantageException " + error.toString());
    }
}