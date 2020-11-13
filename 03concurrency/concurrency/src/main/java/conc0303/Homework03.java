package conc0303;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

//        int result = sum(); //这是得到的返回值

        firstMethod();
        secondMethod();
        thirdMethod();

        // 确保  拿到result 并输出
//        System.out.println("异步计算结果为："+result.get());
         
        System.out.println("总使用时间："+ (System.currentTimeMillis()-start) + " ms");
        
        // 然后退出main线程
    }
    
    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }


    public static void firstMethod() throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        FutureTask<Integer> result = new FutureTask<>(new MyThread());
        new Thread(result).start();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result.get());
        System.out.println("方法一使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }


    static class MyThread implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            return sum();
        }
    }

    public static void secondMethod() throws InterruptedException {
        long start=System.currentTimeMillis();
        final MyObj obj = new MyObj();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                obj.setResult(sum());
            }
        });
        thread.start();
        thread.join();
        System.out.println("异步计算结果为："+obj.getResult());
        System.out.println("方法二使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    static class MyObj {
        private int result;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }
    }

    public static void thirdMethod() throws InterruptedException {
        long start=System.currentTimeMillis();
        LockObj obj = new LockObj();
        Thread task = new Thread(new Runnable() {
            @Override
            public void run() {
                obj.setResult(sum());
            }
        });
        task.start();
        int result = obj.getResult();
//        Thread.sleep(3);
        System.out.println("异步计算结果为："+result);
        System.out.println("方法三使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    static class LockObj {
        private int result;

        public synchronized int getResult() throws InterruptedException {
            while (0 == result){
                System.out.println("wait------------------");
                this.wait();
//                this.notifyAll();
            }
            return result;
        }

        public synchronized void setResult(int result) {
            System.out.println("notify----------------------");
            this.result = result;
            this.notifyAll();
        }
    }

}
