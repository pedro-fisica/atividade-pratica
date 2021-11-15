import com.opencsv.CSVReaderBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
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

public class Principal  {

    public static void main(String[] args) throws IOException {

        Reader reader = Files.newBufferedReader(Paths
                .get("src", "main", "resources", "dados_ex1.csv"));

        Iterator<String[]> iterator = new CSVReaderBuilder(reader)
                                        .withSkipLines(1)
                                        .build()
                                        .readAll()
                                        .iterator();

        List<Variaveis> variaveisList = new ArrayList<>();
        while (iterator.hasNext()) {
            Variaveis variaveis = new Variaveis();
            String[] valor = iterator.next();
            variaveis.setDistancia(valor[1]);
            variaveis.setTemperatura(valor[2]);
            variaveis.setRho(valor[3]);
            variaveis.setKappa(valor[4]);
            variaveis.setProfundidade(variaveisList.isEmpty() ? null : variaveisList.get(variaveisList.size()-1));
            variaveisList.add(variaveis);
        }

        JFrame janela = new JFrame("Meu primeiro frame em Java");
        janela.setVisible(true);

        final XYSeries series = new XYSeries("Random Data");
        for (Variaveis variavel : variaveisList) {
            series.add(variavel.getProfundidade(), variavel.getTemperatura());
        }

        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Series Demo",
                "X",
                "Y",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        janela.add(new ChartPanel(chart));

    }
}
