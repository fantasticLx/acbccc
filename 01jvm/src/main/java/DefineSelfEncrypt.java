import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @ClassName DefineSelfEncrypt
 * @Description 自定义文件加密密码，首次执行为加密文件，再对加密文件执行即为解密。
 * @Author lenovo
 * @Date 2020/10/19 10:27
 * @Version 1.0
 **/
public class DefineSelfEncrypt {

  public static void main(String[] args) {

    Scanner in = new Scanner(System.in);

    System.out.println("请输入要处理文件的绝对路径：");
    String path = in.next();

    System.out.println("请输入本次加密口令：");
    String tocken = in.next();

    System.out.println(dealFileBySelfConf(path, tocken));;
  }

  private static String dealFileBySelfConf(String path, String tocken) {
    byte[] bytes = null;
    try {
      bytes = Files.readAllBytes(Paths.get(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
    // 文件中的每个字节依次重新编码
    for (int j=0;j<bytes.length;j++){
      bytes[j] = (byte) (Integer.parseInt(tocken) - bytes[j]);
    }

    // 生成编码后的同目录文件
    File file = new File(path);
    File directory = file.getParentFile();
    // 处理后文件名
    String fileName = file.getName()+ "._" + tocken + "_";
    System.out.println("文件处理后所在路径：" + directory);
    OutputStream os = null;
    try {
      os = new FileOutputStream(new File(directory, fileName));
      os.write(bytes);
      os.flush();
    } catch (FileNotFoundException e){
      e.printStackTrace();
    } catch (IOException e){
      e.printStackTrace();
    } finally {
      if (os != null){
        try{
          os.close();
        }catch (IOException e){
          e.printStackTrace();
        }
      }
    }

    return "deal success";
  }

}
