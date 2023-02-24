package org.vaadin.marcus;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.pulsar.annotation.EnablePulsar;
import org.springframework.pulsar.core.DefaultSchemaResolver;
import org.springframework.pulsar.core.SchemaResolver;
import org.vaadin.marcus.astra.DataStaxAstraProperties;
import org.vaadin.marcus.astra.data.StockPrice;

import java.nio.file.Path;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@EnableConfigurationProperties(DataStaxAstraProperties.class)
@EnablePulsar
@SpringBootApplication
@Theme(value = "stock-data")
public class Application implements AppShellConfigurator {

    @Value("${alphavantage.api.key}")
    private String ALPHA_VANTAGE_API_KEY;

    @Bean
    void AlphaVantageInitializer() {
        // Initialize AlphaVantage Java API client
        Config cfg = Config.builder()
                .key(ALPHA_VANTAGE_API_KEY)
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
    }

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder
                .withCloudSecureConnectBundle(bundle);
    }

    @Bean
    public SchemaResolver.SchemaResolverCustomizer<DefaultSchemaResolver> schemaResolverCustomizer() {
        return (schemaResolver) -> schemaResolver.addCustomSchemaMapping(StockPrice.class, Schema.JSON(StockPrice.class));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
