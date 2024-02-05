/*
 * Anagram zhaidimo serveris
 *
 * Author: Maksim Isajev
 * Description: 1) siuncia uzhshifruota zhodi ir gauna atsakyma
                2) gauta atsakyma apdoroja
                3)jei atsakymas teisingas siulo pratesti zhaidima jei ne -
                siulo TRY AGAIN
 */ 

package javaapplication4;

import java.net.*;
import java.io.*;
import java.lang.Math.*;

public class AnagrServer{
    public static void main(String args[]){
        int port = -1;

        if (args.length != 1){
            System.err.println("USAGE: java EchoServer <port>");
            return;
        }

        try {
            port = Integer.parseInt(args[0]);
        }
        catch(NumberFormatException e){
            System.err.println("Error #1: bad port number");
            return;
        }

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client connected: "+clientSocket.
                    getInetAddress().getHostAddress());
                PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                
                String answer = null;
                String option = null;
                 
                while(!option.equals("q")){
                    int i = (int)(Math.random() * 10);

                    answer = null;
                    Anagram anagram = new Anagram();

                    // message
                    out.println(anagram.getMessage(1));
                    //sending scrambled word
                    out.println(anagram.getScrambledWord(i));
                    //getting answer
                    out.println(anagram.getMessage(2));

                    answer = in.readLine(); // gaunamas is cliento

                    //checking answer
                    while(!anagram.isCorrect(i,answer)){
                        //siunciamas praneshimas apie neteisinga atsakyma
                        out.println(anagram.getMessage(3));
                        //skaitomas kliento siunèiamas atsakymas
                        answer = in.readLine();
                    }

                    //siunciamas praneshimas apie nora pratesti darba
                    out.println(anagram.getMessage(4));
                    //skaitomas kliento siunèiamas pasirinkimas
                    option = in.readLine();
                }
                System.out.println("Client disconnected: "+clientSocket.
                    getInetAddress().getHostAddress());
                in.close();
                out.close();
                clientSocket.close();

            }
        }
        catch (Exception e){
            System.err.println("Error #9999: Unexpected exception: "+
                e.getMessage());
        }
    }
}