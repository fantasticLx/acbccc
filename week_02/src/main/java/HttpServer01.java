import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * @ClassName HttpServer01
 * @Description java实现一个最简的HTTP服务器--单线程版
 * @Author Shaldon
 * @Date 2020/10/24 18:49
 * @Version 1.0
 */
public class HttpServer01 {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger("HttpServer01");
        ServerSocket serverSocket = new ServerSocket(8801);
        while (true){
            try {
                Socket socket = serverSocket.accept();
                logger.info("accepted....................");
                service(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void service(Socket socket) {
        try{
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.println("hello nio");
            printWriter.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
                e.printStackTrace();
        }
    }
}
