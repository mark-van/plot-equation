import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.Scanner;

// pages at https://coderslegacy.com/java/jfreechart-with-javafx/ and the JfreeCHart documentation were instrumental
// in the implementation of this program
public class task3 extends Application{

    static public String equation;
    public static void main(String[] args) {
        launch(args); //sets up program as a fcx application, then after things are setup, start is called
        //this is standard for javafx(you cna pretty much ignore main for fx applicaitons)
    }

    public static JFreeChart createChart() {
        XYSeries series1 = new XYSeries("First");
        //lets try to call
        for (int i = 0; i < 100; i++) {
            int temp = Task1.equationCalculator(equation, i);
            System.out.println(temp);
            series1.add(i, temp);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);


        JFreeChart chart = ChartFactory.createXYLineChart(
                "Your Equation", // chart title
                "X", // x axis label
                "Y", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );
        chart.setBackgroundPaint(Color.white);
        return chart;
    }

    @Override
    public void start(Stage stage) throws Exception {
        //ok lets take in the users input at this point
        //then we'll generate the data points given the function
        Scanner input = new Scanner(System.in);
        System.out.println("Input an equation of the form y = (expression containing x)");
        equation = input.nextLine();

        //System.out.println(Task1.equationCalculator(equation, 4));

        // get a reference to the plot for further customisation...

        JFreeChart chart = createChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ChartViewer viewer = new ChartViewer(chart);
        stage.setScene(new Scene(viewer));
        stage.setTitle("JFreeChart: LineChart");
        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();
    }
}
