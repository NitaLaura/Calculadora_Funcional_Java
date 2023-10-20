/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.calculadora_funcional;
import javax.swing.JOptionPane;
import com.mycompany.calculadora_funcional.Expressoes;

/**
 *
 * @author Nyta
 */
public class Calculadora_Funcional {
    public static void main(String[] args) {
        int escolhaNotacao = Integer.parseInt(JOptionPane.showInputDialog(
            "Escolha a notação da expressão:\n" +
            "1. Infixa\n" +
            "2. Pós-fixa\n" +
            "3. Pré-fixa"
        ));
    
        String expressao = JOptionPane.showInputDialog("Digite a expressão matemática:");
        String expressaoPosFixa = "";
        String expressaoInfixa = "";
        String expressaoPreFixa = "";
        double resultado = 0.0;
        
        Expressoes expressoes = new Expressoes();
        
        switch (escolhaNotacao) {
            case 1:
                expressaoPosFixa = expressoes.converterInfixaParaPosFixa(expressao);
                expressaoPreFixa = expressoes.converterInfixaParaPreFixa(expressao);
                expressaoInfixa = expressao;
                break;
            case 2:
                expressaoInfixa = expressoes.converterPosFixaParaInfixa(expressao);
                expressaoPreFixa = expressoes.converterPosFixaParaPreFixa(expressao);
                expressaoPosFixa = expressao;
                break;
            case 3:
                expressaoPosFixa = expressoes.converterPreFixaParaPosFixa(expressao);
                expressaoInfixa = expressoes.converterPosFixaParaInfixa(expressaoPosFixa);
                expressaoPreFixa = expressao;
                break;
            default:
                JOptionPane.showMessageDialog(null, "Escolha de notação inválida.");
                break;
        }
    
        resultado = expressoes.avaliarExpressaoPosFixa(expressaoPosFixa);
        JOptionPane.showMessageDialog(null,
            "Resultado: " + resultado + "\n" +
            "Expressão na notação infixa: " + expressaoInfixa + "\n" +
            "Expressão na notação pós-fixa: " + expressaoPosFixa + "\n" +
            "Expressão na notação pré-fixa: " + expressaoPreFixa
        );
    }
}