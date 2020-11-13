package conc0303.hw;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName CyclicBarrierDemo
 * @Description TODO
 * @Author Shaldon
 * @Date 2020/11/11 17:24
 * @Version 1.0
 **/
public class CyclicBarrierDemo extends Thread{
  private volatile int result;
  private CyclicBarrier barrier;
  private static final Logger logger = LoggerFactory.getLogger(CyclicBarrierDemo.class);

  public void setBarrier(CyclicBarrier barrier) {
    this.barrier = barrier;
  }

  public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
    long start = System.currentTimeMillis();

    CyclicBarrierDemo task = new CyclicBarrierDemo();
    CyclicBarrier barrier = new CyclicBarrier(1,()->{
      task.getResult();
    });
    task.setBarrier(barrier);
    task.start();
    Thread.sleep(30);
    int result = task.getResult();

    logger.info("异步计算结果为："+result);
    logger.info("使用时间："+ (System.currentTimeMillis()-start) + " ms");
  }

  @Override
  public void run() {
    try {
      setResult(Utils.sum());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (BrokenBarrierException e) {
      e.printStackTrace();
    }
  }

  public int getResult()  {

    logger.info("result="+result);

    return result;
  }

  public void setResult(int result) throws InterruptedException, BrokenBarrierException {
    logger.info("start write----------------------");
    this.result = result;
    logger.info("after writed.........");
    barrier.await();
  }


}
