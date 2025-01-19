package mk.finki.ukim.mk.mseanalyiser.service;// TechnicalIndicatorsService.java (во папката service)
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class TechnicalIndicatorsService {

    // Пресметка на Simple Moving Average (SMA)
    public List<Double> simpleMovingAverage(List<Double> prices, int window) {
        List<Double> sma = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            if (i + 1 >= window) {
                double sum = 0;
                for (int j = i - window + 1; j <= i; j++) {
                    sum += prices.get(j);
                }
                sma.add(sum / window);
            } else {
                sma.add(null);
            }
        }
        return sma;
    }

    // Пресметка на Exponential Moving Average (EMA)
    public List<Double> exponentialMovingAverage(List<Double> prices, int window) {
        List<Double> ema = new ArrayList<>();
        double multiplier = 2.0 / (window + 1);
        double emaPrev = prices.get(0);
        ema.add(emaPrev);

        for (int i = 1; i < prices.size(); i++) {
            double emaCurrent = (prices.get(i) - emaPrev) * multiplier + emaPrev;
            ema.add(emaCurrent);
            emaPrev = emaCurrent;
        }
        return ema;
    }

    // Пресметка на Relative Strength Index (RSI)
    public List<Double> relativeStrengthIndex(List<Double> prices, int window) {
        List<Double> rsi = new ArrayList<>();
        for (int i = 1; i < prices.size(); i++) {
            double change = prices.get(i) - prices.get(i - 1);
            if (i >= window) {
                double avgGain = 0;
                double avgLoss = 0;
                for (int j = i - window + 1; j <= i; j++) {
                    double priceChange = prices.get(j) - prices.get(j - 1);
                    if (priceChange > 0) {
                        avgGain += priceChange;
                    } else {
                        avgLoss -= priceChange;
                    }
                }
                avgGain /= window;
                avgLoss /= window;

                double rs = avgGain / avgLoss;
                double rsiValue = 100 - (100 / (1 + rs));
                rsi.add(rsiValue);
            } else {
                rsi.add(null);
            }
        }
        return rsi;
    }

    // Пресметка на техничките индикатори за различни временски рамки
    public Map<String, List<Double>> calculateIndicatorsForDifferentTimeFrames(List<Double> prices) {
        Map<String, List<Double>> indicators = new HashMap<>();

        // За 1 ден (користиме последни 1 ден)
        List<Double> sma1Day = simpleMovingAverage(prices, 1);
        List<Double> ema1Day = exponentialMovingAverage(prices, 1);
        List<Double> rsi1Day = relativeStrengthIndex(prices, 1);

        // За 1 недела (7 дена)
        List<Double> sma1Week = simpleMovingAverage(prices, 7);
        List<Double> ema1Week = exponentialMovingAverage(prices, 7);
        List<Double> rsi1Week = relativeStrengthIndex(prices, 7);

        // За 1 месец (30 дена)
        List<Double> sma1Month = simpleMovingAverage(prices, 30);
        List<Double> ema1Month = exponentialMovingAverage(prices, 30);
        List<Double> rsi1Month = relativeStrengthIndex(prices, 30);

        // Складирање на резултатите
        indicators.put("SMA_1Day", sma1Day);
        indicators.put("EMA_1Day", ema1Day);
        indicators.put("RSI_1Day", rsi1Day);

        indicators.put("SMA_1Week", sma1Week);
        indicators.put("EMA_1Week", ema1Week);
        indicators.put("RSI_1Week", rsi1Week);

        indicators.put("SMA_1Month", sma1Month);
        indicators.put("EMA_1Month", ema1Month);
        indicators.put("RSI_1Month", rsi1Month);

        return indicators;
    }

    public String generateSignal(List<Double> prices) {
        return "";
    }
}
