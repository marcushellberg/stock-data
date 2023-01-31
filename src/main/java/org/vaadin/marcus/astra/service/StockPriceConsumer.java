package org.vaadin.marcus.astra.service;

import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;
import org.vaadin.marcus.astra.data.StockPrice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class StockPriceConsumer {

    private final Sinks.Many<StockPrice> stockPriceSink = Sinks.many().multicast().directBestEffort();
    private final Flux<StockPrice> stockPrices = stockPriceSink.asFlux();

    @PulsarListener
    private void stockPriceReceived(StockPrice stockPrice) {
        stockPriceSink.tryEmitNext(stockPrice);
    }

    public Flux<StockPrice> getStockPrices() {
        return stockPrices;
    }
}
