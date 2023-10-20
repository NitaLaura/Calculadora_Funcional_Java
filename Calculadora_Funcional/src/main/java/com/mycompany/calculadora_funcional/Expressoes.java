/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.calculadora_funcional;
import java.util.Stack;

/**
 *
 * @author Nyta
 */
public class Expressoes {
    public static double avaliarExpressaoPreFixa(String expressao) {
    Stack<Double> pilha = new Stack<>();
    String[] tokens = expressao.split(" ");

    for (int i = tokens.length - 1; i >= 0; i--) {
        String token = tokens[i];
        if (isNumero(token)) {
            pilha.push(Double.parseDouble(token));
        } else if (isOperador(token)) {
            if (pilha.size() < 2) {
                throw new IllegalArgumentException("Expressão pré-fixa inválida");
            }
            double a = pilha.pop();
            double b = pilha.pop();
            double resultado = realizarOperacao(a, b, token);
            pilha.push(resultado);
        } else {
            throw new IllegalArgumentException("Token pré-fixo inválido: " + token);
        }
    }

    if (pilha.size() != 1) {
        throw new IllegalArgumentException("Expressão pré-fixa inválida");
    }

    return pilha.pop();
    }
    
    public static double avaliarExpressaoPosFixa(String expressao) {
    Stack<Double> pilha = new Stack<>();
    String[] tokens = expressao.split(" ");

    for (String token : tokens) {
        if (isNumero(token)) {
                pilha.push(Double.parseDouble(token));
        } else if (isOperador(token)) {
            if (pilha.size() < 2) {
                throw new IllegalArgumentException("Expressão inválida");
            }
            double b = pilha.pop();
            double a = pilha.pop();
            double resultado = realizarOperacao(a, b, token);
            pilha.push(resultado);
        } else {
            throw new IllegalArgumentException("Token inválido: " + token);
        }
    }

    if (pilha.size() != 1) {
        throw new IllegalArgumentException("Expressão inválida");
    }

    return pilha.pop();
    }

    public static boolean isNumero(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isOperador(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    public static double realizarOperacao(double a, double b, String operador) {
        switch (operador) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Divisão por zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Operador inválido: " + operador);
        }
    }

    public static String converterInfixaParaPosFixa(String expressao) {
        StringBuilder posFixa = new StringBuilder();
        Stack<Character> operadores = new Stack();

        for (int i = 0; i < expressao.length(); i++) {
            char c = expressao.charAt(i);

            if (Character.isDigit(c) || Character.isLetter(c)) {
                posFixa.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operadores.isEmpty() && getPrecedencia(operadores.peek()) >= getPrecedencia(c)) {
                    posFixa.append(" ").append(operadores.pop());
                }
                operadores.push(c);
                posFixa.append(" ");
            } else if (c == '(') {
                operadores.push(c);
            } else if (c == ')') {
                while (!operadores.isEmpty() && operadores.peek() != '(') {
                    posFixa.append(" ").append(operadores.pop());
                }
                operadores.pop();
            }
        }
    
        while (!operadores.isEmpty()) {
            posFixa.append(" ").append(operadores.pop());
        }

        return posFixa.toString().trim();
    }   
    
    public static String converterInfixaParaPreFixa(String expressao) {
        StringBuilder infixaReversa = new StringBuilder();
        for (int i = expressao.length() - 1; i >= 0; i--) {
            char c = expressao.charAt(i);
            switch (c) {
                case '(':
                    infixaReversa.append(')');
                    break;
                case ')':
                    infixaReversa.append('(');
                    break;
                default:
                    infixaReversa.append(c);
                    break;
            }
        }

        String posFixaReversa = converterInfixaParaPosFixa(infixaReversa.toString());
        StringBuilder preFixa = new StringBuilder();
        for (int i = posFixaReversa.length() - 1; i >= 0; i--) {
            preFixa.append(posFixaReversa.charAt(i));
        }

        return preFixa.toString();
    }

    public String converterPosFixaParaPreFixa(String expressaoPosFixa) {
        Stack<String> pilha = new Stack<>();
        String[] tokens = expressaoPosFixa.split(" ");

        for (String token : tokens) {
            if (isNumero(token)) {
                pilha.push(token);
            } else if (isOperador(token)) {
                String operando2 = pilha.pop();
                String operando1 = pilha.pop();
                String novaExpressao = token + " " + operando1 + " " + operando2;
                pilha.push(novaExpressao);
            }
        }

        return pilha.pop();
    }
    
    public String converterPosFixaParaInfixa(String expressaoPosFixa) {
    Stack<String> pilha = new Stack<>();
    String[] tokens = expressaoPosFixa.split(" ");

    for (String token : tokens) {
        if (isNumero(token)) {
            pilha.push(token);
        } else if (isOperador(token)) {
            String operando2 = pilha.pop();
            String operando1 = pilha.pop();
            String novaExpressao = "(" + operando1 + " " + token + " " + operando2 + ")";
            pilha.push(novaExpressao);
        }
    }

    return pilha.pop();
    }
    
    public String converterPreFixaParaPosFixa(String expressaoPreFixa) {
        Stack<String> pilha = new Stack<>();
        String[] tokens = expressaoPreFixa.split(" ");

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (isNumero(token)) {
                pilha.push(token);
            } else if (isOperador(token)) {
                String operando1 = pilha.pop();
                String operando2 = pilha.pop();
                String novaExpressao = operando1 + " " + operando2 + " " + token;
                pilha.push(novaExpressao);
            }
        }

        return pilha.pop();
    }
    
    public static int getPrecedencia(char operador) {
        if (operador == '+' || operador == '-') {
            return 1;
        } else if (operador == '*' || operador == '/') {
            return 2;
        }
        return 0; // Retorna 0 para operadores desconhecidos ou outros caracteres
    }
}