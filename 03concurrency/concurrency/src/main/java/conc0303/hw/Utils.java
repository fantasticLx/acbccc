package conc0303.hw;

/**
 * @ClassName Utils
 * @Description 方法工具类
 * @Author Shaldon
 * @Date 2020/11/10 15:52
 * @Version 1.0
 **/
public class Utils {

  public static int sum() {
    return fibo(36);
  }

  private static int fibo(int a) {
    if ( a < 2)
      return 1;
    return fibo(a-1) + fibo(a-2);
  }
}
