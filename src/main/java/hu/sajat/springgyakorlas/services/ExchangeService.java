package hu.sajat.springgyakorlas.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.sajat.springgyakorlas.exception.ExchangeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ExchangeService {
    private Double amount;

    private String currency;

    @Value("${fixer_access_key}")
    private String access_key;

    @Value("${fixer}")
    private boolean getFixerIo;

    public Double calculateCurrencyToEur(String currency, Double amount) {

        return amount / findCurrency(currency);

    }

    public Double calculateEurToCurrency(String currency, Double amount) {

        return amount * findCurrency(currency);

    }

    private Double findCurrency(String currency) {
        double amountInTheCurrency = 0.0;
        if (!getFixerIo) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(new File("src/main/resources/CurrencyRaty.json"));

                amountInTheCurrency = jsonNode.findValue(currency).asDouble();

            } catch (IOException e) {
                log.error(String.valueOf(e));
            }
            if (amountInTheCurrency == 0) {
                throw new ExchangeException("Exchange ", "Currency not found. ");
            }
            return amountInTheCurrency;

        } else {

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(new URL("http://data.fixer.io/api/latest?" + access_key));

                amountInTheCurrency = jsonNode.findValue(currency).asDouble();

            } catch (IOException e) {
                log.error(String.valueOf(e));
            }
            if (amountInTheCurrency == 0) {
                throw new ExchangeException("Exchange ", "Currency not found. ");
            }
            return amountInTheCurrency;
        }
    }

}
