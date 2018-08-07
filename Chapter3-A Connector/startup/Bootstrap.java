package ex03.pyrmont.startup;

import ex03.pyrmont.connector.http.HttpConnector;
/**
 * 启动类
 * @author Guozhu Zhu
 * @date 2018/8/7
 * @version 1.0
 */
public final class Bootstrap {
	
  public static void main(String[] args) {
    HttpConnector connector = new HttpConnector();
    connector.start();
  }
  
}