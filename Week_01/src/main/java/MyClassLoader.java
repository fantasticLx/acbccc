import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @ClassName MyClassLoader
 * @Description 自定义一个ClassLoader，加载一个Hello.xclass文件，执行hello方法，此文件内容是一个Hello.class文件所有字节（x=255-x)处理后的文件
 * @Author lenovo
 * @Date 2020/10/16 9:15
 * @Version 1.0
 **/
public class MyClassLoader extends ClassLoader{

  public static void main(String[] args)
      throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

    MyClassLoader myClassLoader = new MyClassLoader();
    Class<?> c = myClassLoader.findClass("Hello");
    Method m = c.getMethod("hello");
    m.invoke(c.newInstance());

  }

  @Override
  protected Class<?> findClass(String name) {
    String path = "Hello.xlass";
    byte[] classCode = getClassCode(path);
//    String base64 = "yv66vgAAADQAHAoABgAOCQAPABAIABEKABIAEwcAFAcAFQEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAAVoZWxsbwEAClNvdXJjZUZpbGUBAApIZWxsby5qYXZhDAAHAAgHABYMABcAGAEAE0hlbGxvLCBjbGFzc0xvYWRlciEHABkMABoAGwEABUhlbGxvAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgAhAAUABgAAAAAAAgABAAcACAABAAkAAAAdAAEAAQAAAAUqtwABsQAAAAEACgAAAAYAAQAAAAEAAQALAAgAAQAJAAAAJQACAAEAAAAJsgACEgO2AASxAAAAAQAKAAAACgACAAAABAAIAAUAAQAMAAAAAgAN";
//    byte[] classCode = Base64.getDecoder().decode(base64);
    return defineClass(name, classCode, 0, classCode.length);
  }


  public static byte[] getClassCode(String path) {
    byte[] bytes = null;
    try {
      bytes = Files.readAllBytes(Paths.get(path));
    }catch (IOException e){
      e.printStackTrace();
    }

    if (bytes != null || bytes.length > 0){
      for (int i=0; i<bytes.length; i++){
        bytes[i] = (byte) (255 - bytes[i]);
      }
    }

    return bytes;
  }


}
