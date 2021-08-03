package udp;

import java.net.*;
import java.io.*;

public class UDPServer{
    // Серверный UDP-сокет запущен на этом порту
    public final static int SERVICE_PORT=50001;

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        try{
            // Создайте новый экземпляр DatagramSocket, чтобы получать ответы от клиента
            DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);

      /* Создайте буферы для хранения отправляемых и получаемых данных. Они временно хранят данные в случае задержек связи */
            byte[] receivingDataBuffer = new byte[1024];
            byte[] sendingDataBuffer = new byte[1024];

            /* Создайте экземпляр UDP-пакета для хранения клиентских данных с использованием буфера для полученных данных */
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Waiting for a client to connect...");

            // Получите данные от клиента и сохраните их в inputPacket
            serverSocket.receive(inputPacket);
            System.out.println("successfully recieved data");

            ObjectInputStream objectInputStream2 = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData(), inputPacket.getOffset(), inputPacket.getLength()));
            Calc igorRestoredFromByte = (Calc) objectInputStream2.readObject();
            objectInputStream2.close();
            System.out.println("successfully serealization data");

            // Выведите на экран отправленные клиентом данные
            System.out.println("Sent from the client: "+ igorRestoredFromByte.toString());

            sendingDataBuffer = String.valueOf(igorRestoredFromByte.runCalculate()).getBytes();

            // Получите IP-адрес и порт клиента
            InetAddress senderAddress = inputPacket.getAddress();
            int senderPort = inputPacket.getPort();

            // Создайте новый UDP-пакет с данными, чтобы отправить их клиенту
            DatagramPacket outputPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, senderAddress, senderPort);

            // Отправьте пакет клиенту
            serverSocket.send(outputPacket);

            // Закройте соединение сокетов
            serverSocket.close();
        }
        catch (SocketException e){
            e.printStackTrace();
        }
    }
}