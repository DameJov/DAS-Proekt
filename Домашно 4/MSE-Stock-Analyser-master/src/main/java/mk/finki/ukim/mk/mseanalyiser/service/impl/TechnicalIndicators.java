package mk.finki.ukim.mk.mseanalyiser.service.impl;

import java.util.*;

public class TechnicalIndicators {

    // Пресметка на Simple Moving Average (SMA)
    public static List<Double> simpleMovingAverage(List<Double> prices, int window) {
        List<Double> sma = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            if (i + 1 >= window) {
                double sum = 0;
                for (int j = i - window + 1; j <= i; j++) {
                    sum += prices.get(j);
                }
                sma.add(sum / window);
            } else {
                sma.add(null); // Не може да се пресмета за помалку од window денови
            }
        }
        return sma;
    }

    // Пресметка на Exponential Moving Average (EMA)
    public static List<Double> exponentialMovingAverage(List<Double> prices, int window) {
        List<Double> ema = new ArrayList<>();
        double multiplier = 2.0 / (window + 1);
        double emaPrev = prices.get(0); // Прв EMA е ист како првата цена
        ema.add(emaPrev);

        for (int i = 1; i < prices.size(); i++) {
            double emaCurrent = (prices.get(i) - emaPrev) * multiplier + emaPrev;
            ema.add(emaCurrent);
            emaPrev = emaCurrent;
        }
        return ema;
    }

    // Пресметка на Relative Strength Index (RSI)
    public static List<Double> relativeStrengthIndex(List<Double> prices, int window) {
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
                rsi.add(null); // Не може да се пресмета за помалку од window денови
            }
        }
        return rsi;
    }

    public static void main(String[] args) {
        // Пример на историски податоци (цена на акциите)
        List<Double> prices = Arrays.asList(44.34, 45.12, 44.98, 45.88, 46.56, 47.90, 47.50, 47.20, 48.90, 49.50,
                50.30, 51.00, 50.70, 51.10, 52.00, 53.50, 54.00, 53.90, 55.10);

        // Пресметка на SMA (14 денови)
        List<Double> sma = simpleMovingAverage(prices, 14);
        System.out.println("Simple Moving Average (SMA): " + sma);

        // Пресметка на EMA (14 денови)
        List<Double> ema = exponentialMovingAverage(prices, 14);
        System.out.println("Exponential Moving Average (EMA): " + ema);

        // Пресметка на RSI (14 денови)
        List<Double> rsi = relativeStrengthIndex(prices, 14);
        System.out.println("Relative Strength Index (RSI): " + rsi);
    }
}
