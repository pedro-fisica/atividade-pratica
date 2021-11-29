import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

import static java.awt.Color.*;
import static java.awt.Color.WHITE;
import static java.lang.Math.cos;
import static java.lang.Math.log10;
import static java.lang.Math.toRadians;

public class EscurecimentoLimbo {

    public static void main(String[] args) {

        int n = 10000; //pontos no gráfico
        double theta = 0; //theta inicial
        double increment = 90d/n; //intervalo

        final XYSeries vanHamme = new XYSeries("Van Hamme");
        final XYSeries eddington = new XYSeries("Eddington");

        for (int i = 0; i <= n; i++) {
            vanHamme.add(theta, new FormulaVonHamme().apply(toRadians(theta)));
            eddington.add(theta, new FormulaEddington().apply(toRadians(theta)));
            theta = theta + increment;
        }

        final XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(vanHamme);
        collection.addSeries(eddington);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Escurecimento de limbo",
                "θ(º)",
                "I(θ)/I(θ=0º)",
                collection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(WHITE);
        plot.setDomainGridlinePaint(BLACK);
        plot.setRangeGridlinePaint(BLACK);

        plot.getRangeAxis().setRange(0.3, 1.1);

        JFrame frame = new JFrame("Gráfico do exercício 2");
        frame.add(new ChartPanel(chart));
        frame.setSize(1200, 1000);
        frame.setVisible(true);
    }
}

class FormulaVonHamme implements Function<Double, Double> {

    @Override
    public Double apply(Double theta) {
        double x = 0.648;
        double y = 0.207;

        return 1 - x * (1 - cos(theta)) - y * cos(theta) * log10(cos(theta));
    }
}

class FormulaEddington implements Function<Double, Double> {

    @Override
    public Double apply(Double theta) {

        return 2d/5d + (3d/5d)*cos(theta);
    }
}
