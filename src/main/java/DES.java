import java.util.ArrayList;

/**
 * Created by larsh on 6-7-2016.
 */
public class DES {

    private ArrayList<Double> data;
    private double[] forecastData;
    private double alpha, trend;

    public DES(ArrayList<Double> data, double alpha, double trend) {
        this.data = data;
        this.forecastData = new double[data.size() + 8];
        this.alpha = alpha;
        this.trend = trend;
        computeForecast();
    }

    private void computeForecast() {
        int count = 1;
        double[] smoothingData = new double[data.size()];
        double[] trendData = new double[data.size()];
        forecastData[0] = data.get(0);

        for (int i = 1; i < data.size(); i++) {
            smoothingData[count] = (alpha * data.get(count)) + (1 - alpha) * (smoothingData[count - 1] + trendData[count - 1]);
            trendData[count] = trend * (smoothingData[count] - smoothingData[count - 1]) + (1 - trend) * trendData[count - 1];
            forecastData[count + 1] = smoothingData[count] + trendData[count];
            count++;
        }

        for (int i = data.size(); i < data.size() + 8; i++) {
            forecastData[i] = smoothingData[data.size()-1] + (i - data.size()) * trendData[data.size()-1];
        }
    }

    public double[] getForecastData() {
        return forecastData;
    }
}
