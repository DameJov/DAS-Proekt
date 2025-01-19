package mk.finki.ukim.mk.mseanalyiser.service;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/technical-analysis")
public class TechnicalIndicatorsController {

    private final TechnicalIndicatorsService technicalIndicatorsService;

    public TechnicalIndicatorsController(TechnicalIndicatorsService service) {
        this.technicalIndicatorsService = service;
    }

    @GetMapping("/{stock}")
    public Map<String, String> getTradeSignal(@PathVariable String stock) {
        // Генерирај случајни податоци за цена на акцијата во последните 30 дена
        List<Double> prices = generateRandomPrices(30);

        String signal = technicalIndicatorsService.generateSignal(prices);
        return Map.of("stock", stock, "signal", signal);
    }

    private List<Double> generateRandomPrices(int days) {
        Random random = new Random();
        List<Double> prices = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            prices.add(100 + random.nextDouble() * 10); // Случајна цена помеѓу 100 и 110
        }
        return prices;
    }
}
