package conc0303.hw;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Logger;

/**
 * @ClassName FutureTaskDemo
 * @Description 在main函数启动一个新线程或线程池，异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * @Author Shaldon
 * @Date 2020/11/10 16:20
 * @Version 1.0
 **/
public class FutureTaskDemo {
  private static Logger logger = Logger.getLogger(FutureTaskDemo.class.getName());

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    long start = System.currentTimeMillis();

    FutureTask<Integer> task = new FutureTask(new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        return Utils.sum();
      }
    });
    new Thread(task).start();
    int result = task.get();

    logger.info("异步计算结果为："+result);
    logger.info("使用时间："+ (System.currentTimeMillis()-start) + " ms");
  }
}
