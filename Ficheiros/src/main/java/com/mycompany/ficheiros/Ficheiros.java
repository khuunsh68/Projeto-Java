/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ficheiros;

import java.util.*;
import java.io.*;

/**
 *
 * @author goncalo farias
 */
public class Ficheiros {

    public static void main(String[] args) {

        // Leitura do arquivo de respostas dos alunos
        List<List<Integer>> respostasAlunos = new ArrayList<>(); // Utilizado para armazenar várias listas de respostas, uma lista para cada aluno
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\gonca\\Desktop\\Projeto-Java\\respostasAlunos.txt"))) { // Vai ler o ficheiro
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
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\gonca\\Desktop\\Projeto-Java\\chaveRespostas.txt"))) {
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

        // Classificação de cada aluno
        List<Double> notasAlunos = new ArrayList<>();
        for (List<Integer> respostas : respostasAlunos) {
            double nota = 0.0;
            for (int i = 0; i < respostas.size(); i++) { // Comparação das respostas dos aliunos com as corretas
                if (respostas.get(i) == chaveRespostas.get(i)) {
                    nota += 1; // 1 ponto pela resposta certa
                } else if (respostas.get(i) != null) { // Resposta errada e diferente de resposta em branco(null) também
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

        // Nota mais alta e nota mais baixa
        double notaMax = notasAlunos.get(0); // notaMax inicializado com o 1 elemento da lista
        double notaMin = notasAlunos.get(0); // notMin inicializado com o 1 elemento da lista
        for (int i = 1; i < notasAlunos.size(); i++) { // Feito um loop para comparar a restante lista com as variáveis
            double nota = notasAlunos.get(i);
            if (nota > notaMax) {
                notaMax = nota; // Máxima nota
            }
            if (nota < notaMin) {
                notaMin = nota; // Mínima nota
            }
        }

        // Média das notas
        double somaNotas = 0;
        double mediaNotas = 0;
        for (double nota : notasAlunos) {
            somaNotas += nota;
        }
        mediaNotas = somaNotas / notasAlunos.size(); // Média final a dividir pelos 30 alunos

        // Número de positivas e respetiva percentagem
        int numPositivas = 0;
        for (double nota : notasAlunos) {
            if (nota >= 10) {
                numPositivas++;
            }
        }
        double percPositivas = ((double) numPositivas / notasAlunos.size()) * 100; // Em percentagem

        // Número de negativas e respetiva percentagem
        int numNegativas = 0;
        numNegativas = notasAlunos.size() - numPositivas;
        double percNegativas = ((double) numNegativas / notasAlunos.size()) * 100; // Em percentagem

        // Exibe informação no ecrâ
        String info = "Classificacao de cada aluno:\n";
        for (int i = 0; i < notasAlunos.size(); i++) {
            info += "Aluno " + (i + 1) + ": " + notasAlunos.get(i) + "\n";
        }
        info += "Nota mais alta: " + notaMax + "\n";
        info += "Nota mais baixa: " + notaMin + "\n";
        info += "Media das notas: " + mediaNotas + "\n";
        info += "Numero de positivas: " + numPositivas + "(" + percPositivas + "%)" + "\n";
        info += "Numero de negativas: " + numNegativas + "(" + percNegativas + "%)" + "\n";

        System.out.println(info); // Print de info

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

        // Guarda as informações num ficheiro de texto criado
        try {
            FileWriter fw = new FileWriter("C:\\Users\\gonca\\Desktop\\Projeto-Java\\informacoes.txt"); // Cria um ojeto FiWriter e define o nome do ficheiro
            fw.write(info); // Chama o metodo write para escrever a String info no ficheiro
            fw.close(); // Fecha o ficheiro aberto em modo escrita
        } catch (IOException e) { // Exceções de IO
            System.out.println("Erro ao salvar informacoes no arquivo!");
        }
    }
}