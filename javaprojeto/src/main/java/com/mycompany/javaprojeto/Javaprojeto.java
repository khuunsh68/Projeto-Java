/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.javaprojeto;

import java.util.*;
import java.io.*;

class Avaliacao {
    private List<List<Integer>> respostas;
    private List<Integer> chave;
    private List<Double> notas;
    private double notaMax;
    private double notaMin;
    private double mediaNotas;
    private int numPositivas;
    private int numNegativas;
    private double percPositivas;
    private double percNegativas;
    private String output;
    
    public Avaliacao(List<List<Integer>> respostas, List<Integer> chave) {
        this.respostas = respostas;
        this.chave = chave;
    }
    
    public void classificarAlunos() {
        // Calcula as notas e estatísticas
        notas = new ArrayList<>();
        for (int i = 0; i < respostas.size(); i++) {
            List<Integer> resposta = respostas.get(i);
            double nota = 0;
            boolean emBranco = true;
            for (int j = 0; j < resposta.size(); j++) { // Comparação das respostas com a chave de respostas
                int comparacao = resposta.get(j);
                if (comparacao != 0) {
                    emBranco = false;
                    if (comparacao == chave.get(j)) {
                        nota += 1; // Resposta certa, 1 valor
                    } else {
                        nota -= 0.5; // Resposta errada, -0.5 valores
                    }
                }
            }
            if (emBranco) {
                // Ignora respostas em branco
                continue;
            }

            // Restrição para quando a nota do aluno for inferior a 0 ser dada a nota final de 0
            if (nota >= 0) {
                notas.add(nota);
            } else {
                notas.add(0.0);
            }
        }
    }
    
    public void calcularNotaMaxMin() {
        notaMax = notas.get(0); // notaMax inicializado com o 1 elemento da lista
        notaMin = notas.get(0); // notaMin inicializado com o 1 elemento da lista

        for (int i = 1; i < notas.size(); i++) {
            double nota = notas.get(i);
            if (nota > notaMax) {
                notaMax = nota; // Máxima nota
            }
            if (nota < notaMin) {
                notaMin = nota; // Mínima nota
            }
        }
    }
    
    public void calcularMedia() {
        // Média final das notas
        double somaNotas = 0;
        for (double nota : notas) {
            somaNotas += nota;
        }
        mediaNotas = somaNotas / notas.size(); // Média final a dividir pelos 30 alunos
    }
    
    public void calcularPositivasNegativas() {
        // Calcular positivas
        numPositivas = 0;
        for (double nota : notas) {
            if (nota >= 10) {
                numPositivas++;
            }
        }
        percPositivas = ((double) numPositivas / notas.size()) * 100; // Em percentagem

        // Calcular negativas
        numNegativas = 0;
        numNegativas = notas.size() - numPositivas;
        percNegativas = ((double) numNegativas / notas.size()) * 100; // Em percentagem
    }
    
    public String getOutput() {
        return output;
    }
    
    public void exibirInformacoes() {
        // Output das informações
        output = "";
        output += "Classificação de cada aluno:\n";
        for (int i = 0; i < notas.size(); i++) {
            output += "Aluno " + (i + 1) + ": " + notas.get(i) + "\n";
        }
        output += "Nota mais alta: " + notaMax + "\n";
        output += "Nota mais baixa: " + notaMin + "\n";
        output += "Media das notas: " + String.format(Locale.US, "%.2f", mediaNotas) + "\n";
        output += "Numero de positivas: " + numPositivas + "(" + String.format(Locale.US, "%.2f", percPositivas) + "%)" + "\n";
        output += "Numero de negativas: " + numNegativas + "(" + String.format(Locale.US, "%.2f", percNegativas) + "%)" + "\n";

        System.out.println(output);
    }
}

// Class que cria e guarda as informações anteriores no ficheiro criado
class FicheiroInformacao {
    
    public void criaFicheiro() {
    // Cria o ficheiro de informação se o mesmo ainda não existir
    File file = new File("C:\\Users\\gonca\\Desktop\\Projeto-Java\\informacoes.txt");

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

    public void guardaInformacoes(String output) {
        // Guarda as informações num ficheiro de texto criado
        try {
            FileWriter fw = new FileWriter("C:\\Users\\gonca\\Desktop\\Projeto-Java\\informacoes.txt"); // Cria um ojeto FiWriter e define o nome do ficheiro
            fw.write(output); // Chama o metodo write para escrever a String info no ficheiro
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
public class Javaprojeto {

    public static void main(String[] args) throws Exception {

        // Lê o arquivo com a chave de respostas
        String ficheiroChave = "C:\\Users\\gonca\\Desktop\\Projeto-Java\\respostas.txt";
        Scanner chaveScanFicheiro = new Scanner(new File(ficheiroChave));
        List<Integer> chave = new ArrayList<>();
        while (chaveScanFicheiro.hasNextInt()) {
            chave.add(chaveScanFicheiro.nextInt());
        }
        chaveScanFicheiro.close();
        
        // Lê o arquivo de objetos com as respostas dos alunos
        String ficheiroRespostas = "C:\\Users\\gonca\\Desktop\\Projeto-Java\\respostasTurma";
        ObjectInputStream obj = new ObjectInputStream(new FileInputStream(ficheiroRespostas));
        List<List<Integer>> respostas = (List<List<Integer>>) obj.readObject();
        obj.close();
        
        // Métodos da Classe Avaliação chamados
        Avaliacao avaliacao = new Avaliacao(respostas, chave);
        avaliacao.classificarAlunos();
        avaliacao.calcularNotaMaxMin();
        avaliacao.calcularMedia();
        avaliacao.calcularPositivasNegativas();
        avaliacao.exibirInformacoes();

        // Para obter variável output da Classe Avaliação de modo a utilizar na Classe FicheiroInformação
        String output = avaliacao.getOutput();
        
        // Métodos da Classe FicheiroAvaliação chamados
        FicheiroInformacao fi = new FicheiroInformacao();
        fi.criaFicheiro();
        fi.guardaInformacoes(output);
    }
}
