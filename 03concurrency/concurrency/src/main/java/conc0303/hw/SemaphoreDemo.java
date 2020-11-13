package conc0303.hw;

import java.util.concurrent.Semaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName SemaphoreDemo
 * @Description TODO
 * @Author Shaldon
 * @Date 2020/11/11 16:52
 * @Version 1.0
 **/
public class SemaphoreDemo {

  private volatile int result;
  private Semaphore semaphore;

  public SemaphoreDemo(Semaphore semaphore) {
    this.semaphore = semaphore;
  }

  private static Logger logger = LoggerFactory.getLogger(SemaphoreDemo.class);

  public static void main(String[] args) throws InterruptedException {
    long start = System.currentTimeMillis();

    SemaphoreDemo obj = new SemaphoreDemo(new Semaphore(1));
    new Thread(()->{
      try {
        obj.setResult(Utils.sum());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
    Thread.sleep(5);
    int result = obj.getResult();

    logger.info("异步计算结果为："+result);
    logger.info("使用时间："+ (System.currentTimeMillis()-start) + " ms");
  }

  public int getResult() throws InterruptedException {
    while (true){
      semaphore.acquire();
      if (0 == result){
        logger.info("wait------------------");
        semaphore.release();
        Thread.sleep(20);
        logger.info("after wait.........");
      }else {
        semaphore.release();
        logger.info("data are ready.........");
        break;
      }
    }
    logger.info("result="+result);

    return result;
  }

  public void setResult(int result) throws InterruptedException {
    semaphore.acquire();
    logger.info("start write----------------------");
    this.result = result;
    logger.info("writed....................");
    semaphore.release();
    logger.info("after writed.........");
  }


}
