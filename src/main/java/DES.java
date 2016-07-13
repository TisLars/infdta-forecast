import java.util.ArrayList;

/**
 * Created by larsh on 6-7-2016.
 */
public class DES {

    private ArrayList<Double> data;
    private double[] forecastData, smoothingData, trendData;
    private double alpha, trend;
    private double sumSquaredErrors, newSumSquaredErrors;

    public DES(ArrayList<Double> data, int forecastPeriod) {
        this.data = data;
        this.forecastData = new double[data.size() + forecastPeriod];
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

        System.out.println("DES: \'alpha\': " + this.alpha + "\t\'trend\': " + trend + "\t\'SSE\': " + calculateSSE());
    }

    private void computeForecast(double alpha, double trend) {
        smoothingData = new double[data.size()];
        trendData = new double[data.size()];

        smoothingData[1] = data.get(1);
        trendData[1] = data.get(0) - data.get(1);

        for (int i = 2; i < data.size(); i++) {
            smoothingData[i] = (alpha * data.get(i)) + (1 - alpha) * (smoothingData[i - 1] + trendData[i - 1]);
            trendData[i] = trend * (smoothingData[i] - smoothingData[i - 1]) + (1 - trend) * trendData[i - 1];
        }

        for (int i = 0; i < forecastData.length; i++) {
            if (i < 2)
                forecastData[i] = 177;
            else if (i >= smoothingData.length)
                forecastData[i] = smoothingData[data.size() - 1] + (i - data.size()) * trendData[data.size() - 1];
            else
                forecastData[i] = smoothingData[i-1] + trendData[i-1];
        }
    }

    public double calculateSSE() {
        double sum = 0;
        for (int i = 0; i < data.size(); i++) {
            sum += (Math.pow(forecastData[i] - data.get(i), 2)) / (data.size() - 2);
        }
        double error = Math.sqrt(sum);

        return error;
    }

    public double[] getData() {
        double result[] = new double[forecastData.length];
        for (int i=0;i<result.length;i++) {
            if (i< data.size())
                result[i] = smoothingData[i];
            else
                result[i] = forecastData[i];
        }
        return result;
    }

    public double[] getForecastData() {
        return forecastData;
    }
}
