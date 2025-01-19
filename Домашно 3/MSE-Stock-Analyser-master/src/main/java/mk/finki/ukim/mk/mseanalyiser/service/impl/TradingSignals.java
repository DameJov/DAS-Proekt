package mk.finki.ukim.mk.mseanalyiser.service.impl;

import java.util.*;

public class TradingSignals {

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
                sma.add(null);
            }
        }
        return sma;
    }

    // Пресметка на Exponential Moving Average (EMA)
    public static List<Double> exponentialMovingAverage(List<Double> prices, int window) {
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
                rsi.add(null);
            }
        }
        return rsi;
    }

    // Генерирање на сигнали за купување, продавање или задржување
    public static List<String> generateSignals(List<Double> prices, List<Double> rsi, List<Double> sma, List<Double> ema) {
        List<String> signals = new ArrayList<>();

        for (int i = 0; i < prices.size(); i++) {
            String signal = "Hold"; // Подразумевано е задржување

            // Генерирање сигнал за RSI
            if (rsi.get(i) != null) {
                if (rsi.get(i) < 30) {
                    signal = "Buy";
                } else if (rsi.get(i) > 70) {
                    signal = "Sell";
                }
            }

            // Генерирање сигнал за SMA
            if (i > 0 && sma.get(i) != null && sma.get(i - 1) != null) {
                if (sma.get(i) > sma.get(i - 1)) {
                    signal = "Buy";
                } else if (sma.get(i) < sma.get(i - 1)) {
                    signal = "Sell";
                }
            }

            // Генерирање сигнал за EMA
            if (i > 0 && ema.get(i) != null && ema.get(i - 1) != null) {
                if (ema.get(i) > ema.get(i - 1)) {
                    signal = "Buy";
                } else if (ema.get(i) < ema.get(i - 1)) {
                    signal = "Sell";
                }
            }

            signals.add(signal);
        }

        return signals;
    }

    public static void main(String[] args) {
        // Пример на историски податоци за цените на акциите
        List<Double> prices = Arrays.asList(44.34, 45.12, 44.98, 45.88, 46.56, 47.90, 47.50, 47.20, 48.90, 49.50,
                50.30, 51.00, 50.70, 51.10, 52.00, 53.50, 54.00, 53.90, 55.10);

        // Пресметка на индикаторите
        List<Double> sma = simpleMovingAverage(prices, 14);
        List<Double> ema = exponentialMovingAverage(prices, 14);
        List<Double> rsi = relativeStrengthIndex(prices, 14);

        // Генерирање сигнали
        List<String> signals = generateSignals(prices, rsi, sma, ema);

        // Печатење на сигнали
        System.out.println("Трговски сигнали:");
        for (int i = 0; i < signals.size(); i++) {
            System.out.println("Ден " + (i + 1) + ": " + signals.get(i));
        }
    }
}
