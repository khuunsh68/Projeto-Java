/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.fcheirosclasses;

import java.util.*;
import java.io.*;

class Avaliacao {

    private List<List<Integer>> respostasAlunos;
    private List<Integer> chaveRespostas;
    private List<Double> notasAlunos;
    private double notaMax;
    private double notaMin;
    private double mediaNotas;
    private int numPositivas;
    private int numNegativas;
    private double percPositivas;
    private double percNegativas;
    private String info;

    // Construtor
    public Avaliacao(List<List<Integer>> respostasAlunos, List<Integer> chaveRespostas) {
        this.respostasAlunos = respostasAlunos;
        this.chaveRespostas = chaveRespostas;
    }

    public void classificarAlunos() {
        notasAlunos = new ArrayList<>();
        for (List<Integer> respostas : respostasAlunos) {
            double nota = 0.0;
            for (int i = 0; i < respostas.size(); i++) { // Comparação das respostas dos alunos com as corretas
                if (respostas.get(i) == chaveRespostas.get(i)) {
                    nota += 1; // 1 ponto pela resposta certa
                } else if (respostas.get(i) != 0) { // Resposta errada e diferente de 0
                    nota -= 0.5; // -0.5 pontos pela resposta errada
                }
            }
            // Restrição para quando a nota do aluno for inferior a 0 ser dada a nota final de 0
            if(nota >= 0) {
                notasAlunos.add(nota);
            } else {
                notasAlunos.add(0.0);
            }
        }
    }

    public void calcularNotaMaxMin() {
        notaMax = notasAlunos.get(0); // notaMax inicializado com o 1 elemento da lista
        notaMin = notasAlunos.get(0); // notMin inicializado com o 1 elemento da lista
        for (int i = 1; i < notasAlunos.size(); i++) { // Feito um loop para comparar a restante lista com as variáveis
            double nota = notasAlunos.get(i);
            if (nota > notaMax) {
                notaMax = nota; // Máxima nota
            }
            if (nota < notaMin) {
                notaMin = nota; // Mínima nota
            }
        }
    }

    public void calcularMedia() {
        double somaNotas = 0;
        for (double nota : notasAlunos) {
            somaNotas += nota;
        }
        mediaNotas = somaNotas / notasAlunos.size(); // Média final a dividir pelos 30 alunos
    }

    public void calcularPositivas() {
        numPositivas = 0;
        for (double nota : notasAlunos) {
            if (nota >= 10) {
                numPositivas++;
            }
        }
        percPositivas = ((double) numPositivas / notasAlunos.size()) * 100; // Em percentagem
    }

    public void calcularNegativas() {
        numNegativas = notasAlunos.size() - numPositivas;
        percNegativas = ((double) numNegativas / notasAlunos.size()) * 100; // Em percentagem
    }
    
    public String getInfo() { // Método getter para obter a variável info
        return info;
    }

    public void exibirInformacoes() {
        // Vai exibir as informações de Avaliação
        info = "Classificacao de cada aluno:\n";
        for (int i = 0; i < notasAlunos.size(); i++) {
            info += "Aluno " + (i + 1) + ": " + notasAlunos.get(i) + "\n";
        }
        info += "Nota mais alta: " + notaMax + "\n";
        info += "Nota mais baixa: " + notaMin + "\n";
        info += "Media das notas: " + String.format(Locale.US, "%.2f", mediaNotas) + "\n";
        info += "Numero de positivas: " + numPositivas + "(" + String.format(Locale.US, "%.2f", percPositivas) + "%)" + "\n";
        info += "Numero de negativas: " + numNegativas + "(" + String.format(Locale.US, "%.2f", percNegativas) + "%)" + "\n";

        System.out.println(info); // Print de info
    }
}

// Class que cria e guarda as informações anteriores no ficheiro criado
class FicheiroInformacao {

    public void criaFicheiro() {
        // Cria o ficheiro de informação se o mesmo ainda não existir
        File file = new File("C:\\Users\\Lenovo\\OneDrive\\Área de Trabalho\\Projeto-Java\\informacoes.txt");

        if (file.exists()) {
            System.out.println("O arquivo informacoes ja existe!");
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Erro ao criar o arquivo!");
            }
        }
    }
    
    public void guardaInformacoes(String info) {
        // Guarda as informações num ficheiro de texto criado
        try {
            FileWriter fw = new FileWriter("C:\\Users\\Lenovo\\OneDrive\\Área de Trabalho\\Projeto-Java\\informacoes.txt"); // Cria um ojeto FiWriter e define o nome do ficheiro
            fw.write(info); // Chama o metodo write para escrever a String info no ficheiro
            fw.close(); // Fecha o ficheiro aberto em modo escrita
        } catch (IOException e) { // Exceções de IO
            System.out.println("Erro ao salvar informacoes no arquivo!");
        }
    }
}

/**
 *
 * @author goncalo farias
 */
public class FcheirosClasses {

    public static void main(String[] args) {
        // Leitura do arquivo de respostas dos alunos
        List<List<Integer>> respostasAlunos = new ArrayList<>(); // Utilizado para armazenar várias listas de respostas, uma lista para cada aluno
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Lenovo\\OneDrive\\Área de Trabalho\\Projeto-Java\\respostasAlunos.txt"))) { // Vai ler o ficheiro
            String linha;
            // Leva loop porque o arquivo tem uma linha de respostas para cada aluno precisando de loop para trocar de linha
            while ((linha = br.readLine()) != null) {
                String[] respostas = linha.trim().split("\\s+");
                List<Integer> listaRespostas = new ArrayList<>();
                for (String resposta : respostas) {
                    listaRespostas.add(Integer.parseInt(resposta));
                }
                respostasAlunos.add(listaRespostas);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de respostas dos alunos: " + e.getMessage());
            return;
        }

        // Leitura do arquivo de chave de respostas
        List<Integer> chaveRespostas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Lenovo\\OneDrive\\Área de Trabalho\\Projeto-Java\\chaveRespostas.txt"))) {
            // Não leva loop pois a chave de respostas só contem uma linha com todas as corretas
            String linha = br.readLine();
            String[] respostas = linha.trim().split("\\s+");
            for (String resposta : respostas) {
                chaveRespostas.add(Integer.parseInt(resposta));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de chave de respostas: " + e.getMessage());
            return;
        }

        // Métodos da Classe Avaliação chamados
        Avaliacao avaliacao = new Avaliacao(respostasAlunos, chaveRespostas);
        avaliacao.classificarAlunos();
        avaliacao.calcularNotaMaxMin();
        avaliacao.calcularMedia();
        avaliacao.calcularPositivas();
        avaliacao.calcularNegativas();
        avaliacao.exibirInformacoes();
        
        // Para obter variável info da Classe Avaliação de modo a utilizar na Classe FicheiroInformação
        String info = avaliacao.getInfo();
        
        // Métodos da Classe FicheiroAvaliação chamados
        FicheiroInformacao fi = new FicheiroInformacao();
        fi.criaFicheiro();
        fi.guardaInformacoes(info);
    }
}
