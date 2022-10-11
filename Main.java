import java.text.DecimalFormat;
import java.util.*;

public class Main {
//    GRUPO I
//    O Euromilhões é um jogo de sorte ou azar no qual o jogador deve preencher uma chave composta por 5 números de 1 a 50 e 2 estrelas (números) de 1 a 11.

    static double prizePool = 240000000;
    static double[] valoresPremios = {prizePool*0.5, prizePool*0.0261, prizePool*0.0061, prizePool*0.0019,
            prizePool*0.0035, prizePool*0.0037, prizePool*0.0026, prizePool*0.013, prizePool*0.0145,
            prizePool*0.027, prizePool*0.0327, prizePool*0.103, prizePool*0.1659};
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        int op = -1;
        while (op != 0) {
            System.out.println("\n#################");
            System.out.println("Euromilhões");
            System.out.println("#################\n");
            System.out.println("1 - Simular sorteio");
            System.out.println("2 - Criar boletim e verificar se é vencedor");
            System.out.println("3 - Gerar boletim automaticamente e verificar se é vencedor");
            System.out.println("4 - Simular sorteios até ser vencedor");
            System.out.println("0 - Sair");
            op = in.nextInt();

            switch (op) {
                case 0 -> System.out.println("A sair...");
                case 1 -> SimularSorteio();
                case 2 -> CriarBoletimUtilizador();
                case 3 -> CriarBoletimAleatorio();
                case 4 -> SimularVencer();
            }
        }
    }

//    1. Simular um sorteio (sortear uma chave vencedora que deve ser apresentada ao utilizador de forma ordenada).
    private static void SimularSorteio() {
        Chave chaveVencedora = GerarChaveAleatoria();
        System.out.println("Chave vencedora");
        System.out.println(chaveVencedora);
    }

    // Cria chave com números gerados aleatoriamente
    private static Chave GerarChaveAleatoria() {
        Chave chave = new Chave();
        Random rnd = new Random();

        for (int i = 0; i < 5; i++) {
            int numRnd = rnd.nextInt(1, 51);
            if(!chave.getNumeros().contains(numRnd)) {
                chave.getNumeros().add(numRnd);
            } else {
                i--;
            }
        }
        for (int i = 0; i < 2; i++) {
            int estrelaRnd = rnd.nextInt(1,13);
            if(!chave.getEstrelas().contains(estrelaRnd)) {
                chave.getEstrelas().add(estrelaRnd);
            } else {
                i--;
            }
        }
        Collections.sort(chave.getNumeros());
        Collections.sort(chave.getEstrelas());

        return chave;
    }

    // Cria chave com números e estrelas introduzidos pelo utilizador
    private static Chave CriarChaveUtilizador() {
        Chave chave = new Chave();
        for (int j = 0; j < 5; j++) {
            System.out.printf("Introduza o %dº número: ", j+1);
            int tempNum = in.nextInt();
            if(!chave.getNumeros().contains(tempNum) && tempNum > 0 && tempNum < 51) {
                chave.getNumeros().add(tempNum);
            } else {
                System.out.println("Número já introduzido ou inválido. Introduza um novo valor.");
                j--;
            }
        }
        for (int j = 0; j < 2; j++) {
            System.out.printf("Introduza a %dª estrela: ", j+1);
            int tempEstrela = in.nextInt();
            if(!chave.getEstrelas().contains(tempEstrela) && tempEstrela > 0 && tempEstrela < 13) {
                chave.getEstrelas().add(tempEstrela);
            } else {
                System.out.println("Estrela já introduzida ou inválida. Introduza um novo valor.");
                j--;
            }
        }
        return chave;
    }

//    2. Criar um boletim com 1 a 5 chaves (inseridas pelo utilizador) e comparar com a chave vencedora desse sorteio.
    private static void CriarBoletimUtilizador() {
        System.out.println("Quantas chaves quer inserir? (Máximo: 5 chaves)");
        int numChaves = in.nextInt();

        if(numChaves >= 1 && numChaves <= 5) {
            List<Chave> boletim = new ArrayList<>();
            Chave chaveVencedora = GerarChaveAleatoria();

            for (int i = 0; i < numChaves; i++) {
                System.out.printf("\nChave %d\n", i+1);
                Chave tempChave = CriarChaveUtilizador();
                boletim.add(tempChave);
            }
            System.out.println("");
            for (int i = 0; i < boletim.size(); i++) {
                System.out.printf("Chave %d\n", i+1);
                System.out.println(boletim.get(i));
            }
            System.out.println("Chave vencedora");
            System.out.println(chaveVencedora);
            VerificarPremio(chaveVencedora, boletim);
        } else {
            System.out.println("Introduza um número de chaves válido (entre 1 e 5).");
        }
    }

//    3. Criar um boletim com 1 a 5 chaves (criadas aleatoriamente) e comparar com a chave vencedora desse sorteio.
    private static void CriarBoletimAleatorio() {
        System.out.println("Quantas chaves quer inserir? (Máximo: 5 chaves)");
        int numChaves = in.nextInt();

        if(numChaves >= 1 && numChaves <= 5) {
            List<Chave> boletim = new ArrayList<>();
            Chave chaveVencedora = GerarChaveAleatoria();

            for (int i = 0; i < numChaves; i++) {
                boletim.add(GerarChaveAleatoria());
            }

            System.out.println("");
            for (int i = 0; i < boletim.size(); i++) {
                System.out.printf("Chave %d\n", i+1);
                System.out.println(boletim.get(i));
            }
            System.out.println("Chave vencedora");
            System.out.println(chaveVencedora);
            VerificarPremio(chaveVencedora, boletim);
        } else {
            System.out.println("Introduza um número de chaves válido (entre 1 e 5).");
        }
    }

//    4. Simular quantas vezes seria necessário jogar (sempre com a mesma chave) de forma a conseguir ganhar o 1º prémio (5 números e 2 estrelas).
    private static void SimularVencer() {
        boolean vencedor = false;
        int contador = 0;

        Chave chaveUtilizador = GerarChaveAleatoria();

        while(!vencedor) {
            Chave chaveVencedora  = GerarChaveAleatoria();
            contador += 1;
            int contNumeros = 0;
            int contEstrelas = 0;
            for (int i = 0; i < chaveUtilizador.getNumeros().size(); i++) {
                for (int j = 0; j < chaveVencedora.getNumeros().size(); j++) {
                    if(chaveUtilizador.getNumeros().get(i) == chaveVencedora.getNumeros().get(j)) {
                        contNumeros += 1;
                    }
                }
            }
            for (int i = 0; i < chaveUtilizador.getEstrelas().size(); i++) {
                for (int j = 0; j < chaveVencedora.getEstrelas().size(); j++) {
                    if(chaveUtilizador.getEstrelas().get(i) == chaveVencedora.getEstrelas().get(j)) {
                        contEstrelas += 1;
                    }
                }
            }
            System.out.println(contador);
            System.out.printf("Números: %d\nEstrelas: %d\n", contNumeros, contEstrelas);
            if(contNumeros == 5 && contEstrelas == 2) {
                System.out.println("Parabéns! Ganhou o 1º prémio à tentativa " +  contador + "!\n");
                vencedor = true;
            }
        }
    }

    // Verifica se um boletim tem chaves com prémios e imprime o respectivo resultado
    private static void VerificarPremio(Chave chaveVencedora, List<Chave> boletim) {

        System.out.println("-----------------");
        System.out.println("PRÉMIOS");
        System.out.println("-----------------");
        for (int i = 0; i < boletim.size(); i++) {
            System.out.printf("Chave %d\n", i+1);
            int contNumeros = 0;
            int contEstrelas = 0;
            for (int j = 0; j < boletim.get(i).getNumeros().size(); j++) {
                for (int k = 0; k < chaveVencedora.getNumeros().size(); k++) {
                    if(boletim.get(i).getNumeros().get(j) ==  chaveVencedora.getNumeros().get(k)) {
                        contNumeros += 1;
                    }
                }
            }
            for (int j = 0; j < boletim.get(i).getEstrelas().size(); j++) {
                for (int k = 0; k < chaveVencedora.getEstrelas().size(); k++) {
                    if(boletim.get(i).getEstrelas().get(j) == chaveVencedora.getEstrelas().get(k)) {
                        contEstrelas += 1;
                    }
                }
            }
            ImprimirPremio(contNumeros, contEstrelas);
        }
    }

    // Imprime mensagem de acordo com o número de números e estrelas acertados
    private static void ImprimirPremio(int numCertos, int estCertas) {
        DecimalFormat df = new DecimalFormat("0.00");
        if(numCertos == 2 && estCertas ==  0) {
            System.out.println("Parabéns! Ganhou o 13º prémio no valor de " + df.format(valoresPremios[12]) + "€.");
        } else if (numCertos == 2 && estCertas == 1) {
            System.out.println("Parabéns! Ganhou o 12º prémio no valor de " + df.format(valoresPremios[11]) + "€.");
        } else if (numCertos == 1 && estCertas == 2) {
            System.out.println("Parabéns! Ganhou o 11º prémio no valor de " + df.format(valoresPremios[10]) + "€.");
        } else if (numCertos == 3 && estCertas == 0) {
            System.out.println("Parabéns! Ganhou o 10º prémio no valor de " + df.format(valoresPremios[9]) + "€.");
        } else if (numCertos == 3 && estCertas == 1) {
            System.out.println("Parabéns! Ganhou o 9º prémio no valor de " + df.format(valoresPremios[8])  + "€.");
        } else if (numCertos == 2 && estCertas == 2) {
            System.out.println("Parabéns! Ganhou o 8º prémio no valor de " + df.format(valoresPremios[7]) + "€.");
        } else if (numCertos == 4 && estCertas == 0) {
            System.out.println("Parabéns! Ganhou o 7º prémio no valor de " + df.format(valoresPremios[6]) + "€.");
        } else if (numCertos == 3 && estCertas == 2) {
            System.out.println("Parabéns! Ganhou o 6º prémio no valor de " + df.format(valoresPremios[5]) + "€.");
        } else if (numCertos == 4 && estCertas == 1) {
            System.out.println("Parabéns! Ganhou o 5º prémio no valor de " + df.format(valoresPremios[4]) + "€.");
        } else if (numCertos == 4 && estCertas == 2) {
            System.out.println("Parabéns! Ganhou o 4º prémio no valor de " + df.format(valoresPremios[3]) + "€.");
        } else if (numCertos == 5 && estCertas == 0) {
            System.out.println("Parabéns! Ganhou o 3º prémio no valor de " + df.format(valoresPremios[2])  + "€.");
        } else if (numCertos == 5 && estCertas == 1) {
            System.out.println("Parabéns! Ganhou o 2º prémio no valor de " + df.format(valoresPremios[1])  + "€.");
        } else if (numCertos == 5 && estCertas == 2) {
            System.out.println("Parabéns! Ganhou o 1º prémio no valor de " + df.format(valoresPremios[0]) + "€.");
        } else {
            System.out.println("Pouca sorte! Chave sem prémio.\n");
        }
    }
}