import java.net.*;
import java.io.*;

/*
 * Anagram zhaidimo klientas
 *
 * Author: Maksim Isajev
 * Description: 1)gauna praneshima "scramled word:"
                2)gauna uzshifruota zhodi
                3)gauna praneshima apie atsakymo uzhklausa
                4)siunchia atsakyma
                5)jei gauna pranesima kad atsakymas neteisingas -
                      siunchia vel tol kol negaus pranesima kad atsakymas
                      teisingas
                6)gauna praneshima apie zhaidimo pratesima
                7)siunchia pasirinkima
 */

public class AnagrClient {
    public static void main(String args[]){
        int port = -1;

        if (args.length != 2){
            System.err.println("USAGE: java EchoClient <port>");
            return;
        }

        try {
            port = Integer.parseInt(args[1]);
        }
        catch(NumberFormatException e){
            System.err.println("Error #1: bad port number");
            return;
        }

        try {
            Socket socket = new Socket(args[0],port);
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));
            
            String message = null;
            String word = null;
            String answer = null;
            String option = null;

            boolean end = false;
            while(!end){
                System.out.print("> ");
               
                //1)gauna praneshima "scramled word:"
                message = in.readLine(); // gaunamas is serverio
                if (message == null){
                    System.err.println("Error #2: Lost connection");
                    end = true;
                }else {
                    System.out.println(message);
                
                    //2)gauna uzshifruota zhodi
                    word = in.readLine();
                    System.out.println(word);
                    //3)gauna praneshima apie atsakymo uzhklausa
                    message = in.readLine();
                    System.out.println(message);
                    //4)siunchia atsakyma
                    answer = br.readLine();
                    out.println(answer); // siunciamas i sevreri

                     /*5)jei gauna pranesima kad atsakymas neteisingas -
                      *  siunchia vel tol kol
                      *  negaus pranesima kad atsakymas teisingas
                    */
                    message = in.readLine();
                    System.out.println(message);
                    while(message.matches("The answer is incorrect. Try again\n")){                                                      
                        answer = br.readLine();
                        out.println(answer);
                        
                        message = in.readLine();   
                        System.out.println(message);
                    }

                    //6)gauna praneshima apie zhaidimo pratesima
                    message = in.readLine();
                    option = br.readLine();
                    if (option.matches("q"))
                            end = true;
                }
            }
            in.close();
            out.close();
            socket.close();

        }
        catch (Exception e){
            System.err.println("Error #9999: Unexpected exception: "+
                e.getMessage());
        }
    }
}
