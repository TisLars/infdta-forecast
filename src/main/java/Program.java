import org.jfree.ui.RefineryUtilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by larsh on 3-6-2016.
 */
public class Program {

    public static ArrayList<Double> data;
    private static double dataSmootingFactor;
    private static double trendSmootingFactor;

    public static void main(String[] args) {
        ReadData readData = new ReadData();
        data = readData.read();

        SES ses = new SES(data, .8);
        DES des = new DES(data, .8, .4);

//        generateCharts("Line Chart", "Swords forecast SES", ses.getForecastData());
        generateCharts("Line Chart", "Swords forecast DES", des.getForecastData());
    }

    private static Double initAlpha() throws IOException {
        System.out.println("value of dataSmootingFactor: ");
        double value = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = Double.parseDouble(br.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid Format!");
        }

        return value;
    }

    private static void generateCharts(String appTitle, String chartTitle, double[] data) {
        ChartGenerator chart = new ChartGenerator(appTitle, chartTitle, data);

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

    public ArrayList<Double> getOriginalData() {
        return data;
    }
}
