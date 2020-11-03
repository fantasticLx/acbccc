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
        int port = 8801;
        ServerSocket serverSocket = new ServerSocket(port);
        logger.info("server stared... listened at " + port + " ........");
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
            String body = "hello,nio";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
                e.printStackTrace();
        }
    }
}
