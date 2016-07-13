import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;

/**
 * Created by larsh on 3-6-2016.
 */
public class Program {

    public static ArrayList<Double> data;

    public static void main(String[] args) {
        ReadData readData = new ReadData();
        data = readData.read();

        SES ses = new SES(data, 12);
        DES des = new DES(data, 12);

        generateCharts("Line Chart", "Swords forecast SES", ses.getSmoothingData());
        generateCharts("Line Chart", "Swords forecast DES", des.getForecastData());
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
