package br.alan;

import br.alan.commands.RunCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<Thread> processos =new ArrayList<>();
    public static String[] automation = { "run config.txt devices.txt"};

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        for(String l: automation){
            Runnable runnable = Terminal.interpret(l);
            Thread thread = new Thread(runnable);
            thread.start();
            processos.add(thread);
        }

        while (true) {
            String command = sc.nextLine();

            try{

                Runnable runnable = Terminal.interpret(command);
                Thread thread = new Thread(runnable);
                thread.start();
                processos.add(thread);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
