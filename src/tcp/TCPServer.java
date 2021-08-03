package tcp;

import java.io.*;
import java.net.*;

public class TCPServer {

    public static int getCalc(int num1, int num2, String operation) {
        int result = 0;
        char[] charArrayOperation = operation.toCharArray();
        switch (charArrayOperation[0]) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            default:
                System.out.println("Операция не распознана. Повторите ввод.");
        }
        return result;
    }

    public static void main(String[] arg) {
        ServerSocket serverSocket = null; //объявление объекта класса ServerSocket
        Socket clientAccepted = null;//объявление объекта класса Socket
        ObjectInputStream fromClient = null;//объявление байтового потока ввода
        ObjectOutputStream toClient = null;//объявление байтового потока вывода
        Calculator calcRequest = null;

        try {
            System.out.println("server starting....");
            serverSocket = new ServerSocket(3030); //создание сокета сервера для заданного порта
            clientAccepted = serverSocket.accept(); //выполнение метода, который обеспечивает реальное подключение сервера к клиенту
            System.out.println("connection established....");

            fromClient = new ObjectInputStream(new BufferedInputStream(clientAccepted.getInputStream())); //создание потока ввода soos = new
            calcRequest = (Calculator) fromClient.readObject();//объявление строки и присваивание ей данных потока ввода, представленных в виде строки (передано клиентом)

            int result = getCalc((Integer.parseInt(calcRequest._numX)), Integer.parseInt(calcRequest._numY), calcRequest._operation);

            System.out.println("recieved operation: '" + calcRequest._numX + calcRequest._operation + calcRequest._numY + "'");
            System.out.println("and message recieved: '" + calcRequest._clientMessage + "'");

            while (!calcRequest._clientMessage.equals("quite")) { //выполнение цикла: пока строка не будет равна «quite»
                toClient = new ObjectOutputStream(new BufferedOutputStream(clientAccepted.getOutputStream())); //создание потока вывода
                toClient.writeObject(new Calculator(result));//потоку вывода присваивается значение переменной (передается клиенту)
                toClient.flush();
            }

        } catch (Exception e) {
        } finally {
            try {
                fromClient.close();//закрытие потока ввода
   //             toClient.close();//закрытие потока вывода
                clientAccepted.close();//закрытие сокета, выделенного для клиента
                serverSocket.close();//закрытие сокета сервера
            } catch (Exception e) {
                e.printStackTrace();//вызывается метод исключения е
            }


        }
    }
}
