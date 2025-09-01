package edu.puc.utils;

public class Calculator {
    public static int fatorial(int posicao){
        if (posicao <= 1){
            return 1;
        }
        return posicao * fatorial(posicao - 1);
    }
}
