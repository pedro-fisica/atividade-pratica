import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Variaveis {

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

    public void setProfundidade(Variaveis anterior) {
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

        this.profundidade = t_i + ((k_i*p_i + k_f*p_f)/2)*(r_f - r_i);
    }

}
