package ex01.pyrmont;

import java.io.InputStream;
import java.io.IOException;

/**
 * 请求类
 * @author Guozhu Zhu
 * @date 2018/8/7
 * @version 1.0
 *
 */
public class Request {

  private InputStream input;
  private String uri;

  public Request(InputStream input) {
    this.input = input;
  }

  public void parse() {
    // 从套接字读取一组字符
    StringBuffer request = new StringBuffer(2048);
    int i;
    byte[] buffer = new byte[2048];
    try {
      i = input.read(buffer);
    }
    catch (IOException e) {
      e.printStackTrace();
      i = -1;
    }
    for (int j = 0; j < i; j++) {
      request.append((char) buffer[j]);
    }
    uri = parseUri(request.toString());
  }

  //解析请求字符串
    /*
     * requestString形式如下：
     * GET /index.html HTTP/1.1
     * Host: localhost:8081
     * Connection: keep-alive
     * Cache-Control: max-age=0
     * ...
     * 该函数目的就是为了获取/index.html字符串
     */
  private String parseUri(String requestString) {
    int index1, index2;
    index1 = requestString.indexOf(' ');
    if (index1 != -1) {
      index2 = requestString.indexOf(' ', index1 + 1);
      if (index2 > index1)
        return requestString.substring(index1 + 1, index2);
    }
    return null;
  }

  public String getUri() {
    return uri;
  }

}