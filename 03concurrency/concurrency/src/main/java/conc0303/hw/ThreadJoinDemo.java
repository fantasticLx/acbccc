package conc0303.hw;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * @ClassName ThreadJoinDemo
 * @Description TODO
 * @Author Shaldon
 * @Date 2020/11/10 16:27
 * @Version 1.0
 **/
public class ThreadJoinDemo {

  private int result;
  public int getResult() {
    return result;
  }
  public void setResult(int result) {
    this.result = result;
  }
  private static Logger logger = Logger.getLogger(ThreadJoinDemo.class.getName());

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    long start = System.currentTimeMillis();

    ThreadJoinDemo obj = new ThreadJoinDemo();
    Thread task = new Thread(()->{
      obj.setResult(Utils.sum());
    });
    task.start();
    task.join();

    logger.info("异步计算结果为：" + obj.getResult());
    logger.info("使用时间：" + (System.currentTimeMillis() - start) + " ms");
  }
}
