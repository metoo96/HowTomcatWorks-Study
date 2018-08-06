package ex01.pyrmont;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
/**
 * HttpServer类
 * @author Guozhu Zhu
 * @date 2018/8/7
 * @version 1.0
 *
 */
public class HttpServer {
  
  public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
  
  // 关闭连接的命令
  private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

  // 标记是否关闭连接
  private boolean shutdown = false;

  public static void main(String[] args) {
    HttpServer server = new HttpServer();
    server.await();
  }
  
  //启动服务
  public void await() {
    ServerSocket serverSocket = null;
    int port = 8081;
    try {
      //参数1代表tcp请求队列最大容量为1
      serverSocket =  new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
    }
    catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
   
    //循环等到连接请求
    while (!shutdown) {
      Socket socket = null;
      InputStream input = null;
      OutputStream output = null;
      try {
        socket = serverSocket.accept();
        input = socket.getInputStream();
        output = socket.getOutputStream();
      
        // 创建请求对象和解析
        Request request = new Request(input);
        request.parse();
       
        // 创建响应对象
        Response response = new Response(output);
        response.setRequest(request);
        response.sendStaticResource();

        // 关闭套接字
        socket.close();
        System.out.println(request.getUri());
        
        // 是否为shutdown关闭命令
        if (shutdown = request.getUri().equals(SHUTDOWN_COMMAND)) {
          break;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
        continue;
      }
    }
  }
  
}
