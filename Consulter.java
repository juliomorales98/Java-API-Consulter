import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.Console;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.IOException;

import java.util.Scanner;

public class Consulter{

    private static HttpURLConnection connection;
    private static Scanner reader = new Scanner(System.in);
    public static void main(String[] args){
        //XLMVRJ9VOBZPM5J3

        String key = GetInputKey();
        String apiUrl = GetInputUrl();
        reader.close();
        System.out.println(key);
        System.out.println(apiUrl);

    }

    private static String GetInputKey(){
        
        String input;
        boolean validKey = false;

        do{
            System.out.print("Type your API key" + ": ");
            input = reader.nextLine();
            
            if(input.length() < 16 || input.length() > 16){
                System.out.println("Introduced key is not valid. Please try again.");

            }else{
                validKey = true;
            }

        }while(!validKey);
        

        
        return input;
    }

    private static String GetInputUrl(){
        
        String input;

        System.out.print("Type your API's url" + ": ");
        input = reader.nextLine();

        
        return input;
    }

    

    private static String RequestInformation(String apiKey, String urlApi){
        StringBuffer responseContent = new StringBuffer();
        try {
            int status;
            BufferedReader reader;
            String line;
            

            URL url = new URL(urlApi+apiKey);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            status = connection.getResponseCode();
            

            //Tiene que dar un valor en el rango de 200
            if(status > 299){
                //Imprimimos el error
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                
            }else{
                //Imprimimos resultado de api

                //Obtenemos resultado
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null){
                    
                    responseContent.append(line);
                }
            }

            

        } catch (MalformedURLException e) {
            System.out.println(e);
            return null;
        } catch (IOException e){
            System.out.println(e);
            return null;
        }

        return responseContent.toString();
    }
}