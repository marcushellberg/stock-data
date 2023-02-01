package org.vaadin.marcus.endpoints;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import org.vaadin.marcus.astra.data.StockPrice;
import org.vaadin.marcus.astra.data.StockSymbol;
import org.vaadin.marcus.astra.service.StockPriceConsumer;
import org.vaadin.marcus.astra.service.StockPriceProducer;
import org.vaadin.marcus.astra.service.StockPriceService;
import reactor.core.publisher.Flux;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class StockPriceEndpoint {
    private final StockPriceProducer producer;
    private final StockPriceConsumer consumer;
    private final StockPriceService service;


    StockPriceEndpoint(StockPriceProducer producer, StockPriceConsumer consumer, StockPriceService service) {

        this.producer = producer;
        this.consumer = consumer;
        this.service = service;
    }

    public List<StockSymbol> getSymbols() {
        return StockSymbol.supportedSymbols();
    }

    public void produceDataForTicker(String ticker) {
        producer.produceStockPriceData(ticker);
    }

    public Flux<StockPrice> getStockPriceStream() {
        return consumer.getStockPrices();
    }

    public List<StockPrice> findAllByTicker(String ticker) {
        return service.findAllByTicker(ticker);
    }
}
