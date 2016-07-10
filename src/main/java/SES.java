import java.util.ArrayList;

/**
 * Created by larsh on 6-7-2016.
 */
public class SES {

    private ArrayList<Double> data;
    private double[] smoothingData;
    private double alpha;
    private double sumSquaredErrors, newSumSquaredErrors;

    public SES(ArrayList<Double> data) {
        this.data = data;
        this.smoothingData = new double[data.size()];
        this.sumSquaredErrors = Double.MAX_VALUE;

        for (double i = 0.01; i < 1; i += .01) {
            computeForecast(i);
            newSumSquaredErrors = calculateSSE();
            if (newSumSquaredErrors < sumSquaredErrors) {
                sumSquaredErrors = newSumSquaredErrors;
                this.alpha = i;
            }
        }
        computeForecast(this.alpha);
    }

    private void computeForecast(double alpha) {
        int count = 1;
        smoothingData[0] = (data.get(0) + data.get(18)) / 2;

        for (int i = 1; i < data.size(); i++) {
            smoothingData[count] = (alpha * data.get(i - 1)) + (1 - alpha) * smoothingData[count - 1];
            count++;
        }
    }

    public double calculateSSE() {
        double sum = 0;
        for (int i = 0; i < data.size(); i++) {
            sum += (Math.pow(smoothingData[i] - data.get(i), 2)) / (data.size() - 2);
        }
        double error = Math.sqrt(sum);

        return error;
    }

    public double[] getSmoothingData() {
        return smoothingData;
    }
}
