package ex01.pyrmont;

import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;

/**
 * 响应类
 * @author Guozhu Zhu
 * @date 2018/8/7
 * @version 1.0
 * 
 */
public class Response {

  private static final int BUFFER_SIZE = 1024;
  Request request;
  OutputStream output;

  public Response(OutputStream output) {
    this.output = output;
  }

  public void setRequest(Request request) {
    this.request = request;
  }

  public void sendStaticResource() throws IOException {
    byte[] bytes = new byte[BUFFER_SIZE];
    FileInputStream fis = null;
    try {
      //读取请求文件
      File file = new File(HttpServer.WEB_ROOT, request.getUri());
      if (file.exists()) {
        fis = new FileInputStream(file);
        int ch = fis.read(bytes, 0, BUFFER_SIZE);
        String header = "HTTP/1.1 200 OK\r\n" +
                "Content-Type : text/html\r\n\r\n";
        output.write(header.getBytes());
        while (ch != -1) {
          output.write(bytes, 0, ch);
          ch = fis.read(bytes, 0, BUFFER_SIZE);
        }
      }
      else {
        // 文件不存在， 通过回车换行来区分头部和主体内容
        String errorMessage = "HTTP/1.1 404 File Not Found10\r\n" +
          "Content-Type: text/html\r\n" +
          "Content-Length: 23\r\n" +
          "\r\n" +
          "<h1>File Not Found</h1>";
        output.write(errorMessage.getBytes());
      }
    }
    catch (Exception e) {
      System.out.println(e.toString() );
    }
    finally {
      if (fis != null)
        fis.close();
    }
  }
  
}