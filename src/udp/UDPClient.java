package udp;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient {
    /* Порт сервера, к которому собирается
  подключиться клиентский сокет */
    public final static int SERVICE_PORT = 50001;


    public static void main(String[] args) throws IOException{
        try{

      /* Создайте экземпляр клиентского сокета. Нет необходимости в привязке к определенному порту */
            DatagramSocket clientSocket = new DatagramSocket();

            // Получите IP-адрес сервера
            InetAddress IPAddress = InetAddress.getByName("localhost");

            // Создайте соответствующие буферы
            byte[] sendingDataBuffer = new byte[1024];
            byte[] receivingDataBuffer = new byte[1024];

            Scanner in = new Scanner(System.in);
            System.out.println("Input x,y,z pls:");
            Calc c = new Calc(in.nextDouble(),in.nextDouble(),in.nextDouble());
            System.out.println(System.out.format("You've entered X=%s, Y=%s, Z=%s ",c.getX(),c.getY(),c.getZ()));

        /* Преобразуйте данные в байты и разместите в буферах */
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream2.writeObject(c);
            objectOutputStream2.flush();
            sendingDataBuffer = byteArrayOutputStream.toByteArray();

            // Создайте UDP-пакет
            DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer,sendingDataBuffer.length,IPAddress, SERVICE_PORT);

            // Отправьте UDP-пакет серверу
            clientSocket.send(sendingPacket);

            // Получите ответ от сервера, т.е. предложение из заглавных букв
            DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer,receivingDataBuffer.length);
            clientSocket.receive(receivingPacket);

            // Выведите на экране полученные данные
            String receivedData = new String(receivingPacket.getData());
            System.out.println("Sent from the server: "+ receivedData);

            // Закройте соединение с сервером через сокет
            clientSocket.close();
        }
        catch(SocketException e) {
            e.printStackTrace();
        }
    }
}