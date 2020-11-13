package conc0303.hw;

import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName CountDownLatchDemo
 * @Description TODO
 * @Author Shaldon
 * @Date 2020/11/11 10:25
 * @Version 1.0
 **/
public class CountDownLatchDemo {

  private volatile int result;
  private CountDownLatch latch;

  public CountDownLatchDemo(CountDownLatch latch) {
    this.latch = latch;
  }

  private static Logger logger = LoggerFactory.getLogger(CountDownLatchDemo.class);


  public static void main(String[] args) throws InterruptedException {
    long start = System.currentTimeMillis();

    CountDownLatchDemo obj = new CountDownLatchDemo(new CountDownLatch(1));
    new Thread(()->{
      obj.setResult(Utils.sum());
    }).start();
    Thread.sleep(5);
    int result = obj.getResult();

    logger.info("异步计算结果为："+result);
    logger.info("使用时间："+ (System.currentTimeMillis()-start) + " ms");
  }

  public int getResult() throws InterruptedException {

    if (0 == result){
      logger.info("wait------------------");
      latch.await();
      logger.info("after wait.........");
    }
    logger.info("result="+result);

    return result;
  }

  public void setResult(int result) {

    logger.info("start write----------------------");
    this.result = result;
    logger.info("writed....................");
    latch.countDown();
    logger.info("after countdown.........");
  }
}
