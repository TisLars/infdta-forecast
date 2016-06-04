import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * Created by larsh on 4-6-2016.
 */
public class ChartGenerator extends ApplicationFrame {

    public ChartGenerator(String applicationTitle, String chartTitle, double[] data) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Day", "Swords",
                createDataset(data),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(double[] data) {
        Program program = new Program();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int day = 0;
        for (Double value : data) {
            dataset.addValue(program.getCsvData().get(day), "Smoothing", "" + day + "");
            dataset.addValue(value, "Swords", "" + day + "");
            day++;
        }

        return dataset;
    }
}
