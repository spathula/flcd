package com.flcd;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FiniteAutomata fa = new FiniteAutomata();
        System.out.println("1. the set of states \n2. the alphabet \n3. all the transitions \n4. the set of final states");

        while(true) {
            fa.run(scanner.nextInt());
        }
    }
}
