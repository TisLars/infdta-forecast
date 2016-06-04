import org.jfree.ui.RefineryUtilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by larsh on 3-6-2016.
 */
public class Program {

    public static ArrayList<Integer> data;
    private static double alpha;

    public static void main(String[] args) {
        try {
            alpha = initAlpha();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ReadData readData = new ReadData();
        data = readData.read();

        double[] forecastData = computeForecast(data);
        ChartGenerator chart = new ChartGenerator("Line chart", "Swords forecast", forecastData);

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

    private static double[] computeForecast(ArrayList<Integer> data) {
        int timeValue;
        double smoothValue[] = new double[data.size()];
        int count = 0;

        for (Integer dataValue : data) {
            if (count > 0)
                smoothValue[count] = alpha * (dataValue) + (1 - alpha) * (smoothValue[count - 1]);
            System.out.println(dataValue + "\t" + smoothValue[count]);
            count++;
        }

        return smoothValue;
    }

    private static Double initAlpha() throws IOException {
        System.out.println("value of alpha: ");
        double value = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = Double.parseDouble(br.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid Format!");
        }

        return value;
    }

    public ArrayList<Integer> getCsvData() {
        return data;
    }
}
