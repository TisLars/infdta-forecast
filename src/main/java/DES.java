import java.util.ArrayList;

/**
 * Created by larsh on 6-7-2016.
 */
public class DES {

    private ArrayList<Double> data;
    private double[] forecastData;
    private final int FORECASTPERIOD = 12;
    private double alpha, trend;
    private double sumSquaredErrors, newSumSquaredErrors;

    public DES(ArrayList<Double> data) {
        this.data = data;
        this.forecastData = new double[data.size() + FORECASTPERIOD];
        this.sumSquaredErrors = Double.MAX_VALUE;

        for (double i = 0.01; i < 1; i += .01) {
            for (double j = 0.01; j < 1; j += .01) {
                computeForecast(i, j);
                newSumSquaredErrors = calculateSSE();
                if (newSumSquaredErrors < sumSquaredErrors) {
                    sumSquaredErrors = newSumSquaredErrors;
                    this.alpha = i;
                    this.trend = j;
                }
            }
        }
        computeForecast(alpha, trend);
        System.out.println(calculateSSE());
        System.out.println(data.size());
    }

    private void computeForecast(double alpha, double trend) {
        double[] smoothingData = new double[data.size()];

        System.out.println(smoothingData.length);
        double[] trendData = new double[data.size()];
        smoothingData[1] = data.get(1);
        trendData[1] = data.get(0) - data.get(1);

        for (int i = 2; i < data.size(); i++) {
            smoothingData[i] = (alpha * data.get(i)) + (1 - alpha) * (smoothingData[i - 1] + trendData[i - 1]);
            trendData[i] = trend * (smoothingData[i] - smoothingData[i - 1]) + (1 - trend) * trendData[i - 1];
        }

        for (int i = 0; i < forecastData.length; i++) {
            if (i < 2)
                forecastData[i] = (smoothingData[1] + smoothingData[19]) / 2;
            else if (i > smoothingData.length)
                forecastData[i] = smoothingData[data.size() - 1] + (i - data.size()) * trendData[data.size() - 1];
            else
                forecastData[i] = smoothingData[i - 1] + trendData[i - 1];
        }
    }

    public double calculateSSE() {
        double sum = 0;
        for (int i = 0; i < data.size(); i++) {
            sum += (Math.pow(forecastData[i] - data.get(i), 2)) / (data.size() - 3);
        }
        double error = Math.sqrt(sum);

        return error;
    }

    public double[] getForecastData() {
        return forecastData;
    }
}
