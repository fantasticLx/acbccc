package conc0301.op;

/**
 * synchronized( Thread ),对线程对象加同步锁，如果此Thread对象还未获得CPU时间片，则当前线程执行，Thread线程阻塞；如果Thread线程先获得CPU时间片，则当前线程阻塞
 */
public class Join {
    
    public static void main(String[] args) throws InterruptedException {
        Object oo = new Object();
    
        MyThread thread1 = new MyThread("thread1 -- ");
        thread1.setOo(oo);
        thread1.start();
//        Thread.sleep(2);
        synchronized (thread1) {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(100);
                if (i == 20) {
                    try {
                        thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -- " + i);
            }
        }
    }
    
}

class MyThread extends Thread {
    
    private String name;
    private Object oo;
    
    public void setOo(Object oo) {
        this.oo = oo;
    }
    
    public MyThread(String name) {
        this.name = name;
    }
    
    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + i);
            }
        }
    }
    
}