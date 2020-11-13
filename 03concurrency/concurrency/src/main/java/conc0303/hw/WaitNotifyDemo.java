package conc0303.hw;

import java.util.logging.Logger;

/**
 * @ClassName WaitNotifyDemo
 * @Description TODO
 * @Author Shaldon
 * @Date 2020/11/10 16:34
 * @Version 1.0
 **/
public class WaitNotifyDemo {

  private volatile int result;
  private static Logger logger = Logger.getLogger(WaitNotifyDemo.class.getName());

  public static void main(String[] args) throws InterruptedException {
    long start = System.currentTimeMillis();

    WaitNotifyDemo obj = new WaitNotifyDemo();
    new Thread(()->{
      obj.setResult(Utils.sum());
    }).start();
    int result = obj.getResult();

    logger.info("异步计算结果为："+result);
    logger.info("使用时间："+ (System.currentTimeMillis()-start) + " ms");
  }

  public synchronized int getResult() throws InterruptedException {
    while (0 == result){
      System.out.println("wait------------------");
      this.wait();
//    this.notifyAll();
    }
    return result;
  }

  public synchronized void setResult(int result) {
    System.out.println("notify----------------------");
    this.result = result;
    this.notifyAll();
  }
}
