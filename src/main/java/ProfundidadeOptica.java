import com.opencsv.CSVReaderBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

public class ProfundidadeOptica {

    public static void main(String[] args) throws IOException {

        Reader reader = Files.newBufferedReader(Paths
                .get("src", "main", "resources", "dados_ex1.csv"));

        Iterator<String[]> iterator = new CSVReaderBuilder(reader)
                                        .withSkipLines(1)
                                        .build()
                                        .readAll()
                                        .iterator();

        List<VariaveisProfundidadeOptica> variaveisList = new ArrayList<>();
        while (iterator.hasNext()) {
            VariaveisProfundidadeOptica variaveis = new VariaveisProfundidadeOptica();
            String[] linha = iterator.next();
            variaveis.setDistancia(linha[1]);
            variaveis.setTemperatura(linha[2]);
            variaveis.setRho(linha[3]);
            variaveis.setKappa(linha[4]);
            variaveis.setProfundidade(variaveisList.isEmpty() ? null : variaveisList.get(variaveisList.size()-1));
            variaveisList.add(variaveis);
        }

        final XYSeries modelo = new XYSeries("Modelo estelar");
        final XYSeries aproximacao = new XYSeries("Aproximação plano-paralela");
        for (VariaveisProfundidadeOptica variavel : variaveisList) {
            modelo.add(variavel.getProfundidade(), variavel.getTemperatura());
            aproximacao.add(variavel.getProfundidade(), variavel.getTemperaturaAproximada());
        }

        final XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(modelo);
        collection.addSeries(aproximacao);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Profundidade óptica x Temperatura",
                "Profundidade óptica",
                "Temperatura (K)",
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

        JFrame frame = new JFrame("Gráfico do exercício 1");
        frame.add(new ChartPanel(chart));
        frame.setSize(1200, 1000);
        frame.setVisible(true);
    }
}

class VariaveisProfundidadeOptica {

    private double distancia;
    private double temperatura;
    private double rho;
    private double kappa;
    private double profundidade;

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = Double.parseDouble(distancia);
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = Double.parseDouble(temperatura);
    }

    public double getRho() {
        return rho;
    }

    public void setRho(String rho) {
        this.rho = Double.parseDouble(rho);
    }

    public double getKappa() {
        return kappa;
    }

    public void setKappa(String kappa) {
        this.kappa = Double.parseDouble(kappa);
    }

    public double getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(VariaveisProfundidadeOptica anterior) {
        if (anterior == null) {
            this.profundidade = 0;
            return;
        }
        double t_i = anterior.getProfundidade();
        double k_i = anterior.getKappa();
        double p_i = anterior.getRho();
        double r_i = anterior.getDistancia();
        double k_f = this.getKappa();
        double p_f = this.getRho();
        double r_f = this.getDistancia();

        this.profundidade = t_i - ((k_i*p_i + k_f*p_f)/2)*(r_f - r_i);
    }

    public double getTemperaturaAproximada() {
        double t_e = 5504;
        return Math.pow((3d/4d)*Math.pow(t_e, 4)*(getProfundidade() + 2d/3d), 1d/4d);
    }

}
