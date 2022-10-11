import java.util.ArrayList;
import java.util.List;

public class Chave {
    private List<Integer> numeros;
    private List<Integer> estrelas;

    public Chave() {
        numeros = new ArrayList<>();
        estrelas = new ArrayList<>();
    }

    public Chave(List<Integer> numeros, List<Integer> estrelas) {
        this.setNumeros(numeros);
        this.setEstrelas(estrelas);
    }

    public List<Integer> getNumeros() {
        return numeros;
    }

    public void setNumeros(List<Integer> numeros) {
        this.numeros = numeros;
    }

    public List<Integer> getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(List<Integer> estrelas) {
        this.estrelas = estrelas;
    }

    @Override
    public String toString() {
        String texto = "";
        if(numeros.size() == 0 || estrelas.size() == 0) {
            texto += "A chave vencedora ainda não foi sorteada.\n";
        } else {
            texto +=  "Numeros: \t";
            for (int i = 0; i < numeros.size(); i++) {
                if(i != numeros.size()-1) {
                    texto += numeros.get(i) + ", ";
                } else {
                    texto += numeros.get(i) + "\n";
                }
            }
            texto += "Estrelas: \t";
            for (int i = 0; i < estrelas.size(); i++) {
                if(i != estrelas.size()-1) {
                    texto += estrelas.get(i) + ", ";
                } else {
                    texto += estrelas.get(i) + "\n";
                }
            }
        }
        return texto;
    }
}
