package org.vaadin.marcus.astra.service;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.vaadin.marcus.astra.data.StockPrice;

import java.util.List;

public interface StockPriceRepository extends CassandraRepository<StockPrice, String> {
    List<StockPrice> findAllBySymbol(final String symbol);

}
