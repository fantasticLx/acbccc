package conc0303.hw;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * @ClassName LockDemo
 * @Description TODO
 * @Author Shaldon
 * @Date 2020/11/10 17:11
 * @Version 1.0
 **/
public class LockDemo {

  private volatile int result;
  private ReentrantLock lock = new ReentrantLock();
  private Condition isComplete = lock.newCondition();
  private static Logger logger = Logger.getLogger(LockDemo.class.getName());

  public static void main(String[] args) throws InterruptedException {
    long start = System.currentTimeMillis();

    LockDemo obj = new LockDemo();
    new Thread(()->{
      obj.setResult(Utils.sum());
    }).start();
    int result = obj.getResult();

    logger.info("异步计算结果为："+result);
    logger.info("使用时间："+ (System.currentTimeMillis()-start) + " ms");
  }

  public int getResult() throws InterruptedException {
    lock.lock();
    logger.info("get lock....");
    try {
      while (0 == result){
        logger.info("wait------------------");
        isComplete.await();
      }
    } finally {
      lock.unlock();
    }

    return result;
  }

  public void setResult(int result) {

    lock.lock();
    try {
      System.out.println("write----------------------");
      this.result = result;
      isComplete.signal();
    } finally {
      lock.unlock();
    }


  }
}
