package tcp;

import java.io.*;
import java.net.*;

public class TCPClient {


    public static void main(String[] arg){
        ObjectInputStream fromServer = null;
        Calculator calcRequset = null;
        try {

            System.out.println("server connecting....");
            Socket clientSocket = new Socket("localhost",3030); //установление //соединения между локальной машиной и указанным портом узла сети

            System.out.println("connection established....");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); //создание буферизированного символьного потока ввода
            ObjectOutputStream toServer = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream())); //создание потока вывода

            System.out.println("Enter any string to send to server \n\t('quite' − pro-gramme terminate)");

            calcRequset = new Calculator(String.valueOf(calcRequset.getInt()), String.valueOf(calcRequset.getInt()),String.valueOf(calcRequset.getOperation()),stdin.readLine());

            System.out.println("you've entered: " + calcRequset.get_numX() + calcRequset.get_operation() + calcRequset.get_numY());
            System.out.print("and message: " + calcRequset.get_clientMessage());

            //выполнение цикла, пока строка не будет равна «quite»
            while(!calcRequset.get_clientMessage().equals("quite")) {

                toServer.writeObject(calcRequset); //потоку вывода присваивается значение строковой переменной (передается серверу)
                toServer.flush();

                fromServer = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));//создание потока ввода
                Calculator msgFromReply = (Calculator)fromServer.readObject();

                System.out.println("~server~: " + msgFromReply.getResult());//выводится на экран содержимое потока ввода (переданное сервером)
                System.out.println("---------------------------");
            }

            toServer.close();//закрытие потока вывода
        //    fromServer.close();//закрытие потока ввода
            clientSocket.close();//закрытие сокета

        }catch(Exception e)	{
            e.printStackTrace(); //выполнение метода исключения е
        }

    }
}
