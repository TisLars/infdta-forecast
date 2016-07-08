import java.util.ArrayList;

/**
 * Created by larsh on 6-7-2016.
 */
public class SES {

    private ArrayList<Double> data;
    private double[] forecastData;
    private double alpha;

    public SES(ArrayList<Double> data, double alpha) {
        this.data = data;
        this.forecastData = new double[data.size()];
        this.alpha = alpha;
        computeForecast();
    }

    private void computeForecast() {
        int count = 1;
        forecastData[0] = data.get(0);

        for (int i = 1; i < data.size(); i++) {
            forecastData[count] = (alpha * data.get(i - 1)) + (1 - alpha) * forecastData[count - 1];
            count++;
        }
    }

    public double[] getForecastData() {
        return forecastData;
    }
}
