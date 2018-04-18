package cn.com.boke.utils;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2017/7/3.
 * Time: 1:36.
 * Tags: Code, we are serious.
 */

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * base64工具类
 *
 * @author
 * @create 2017-07-03 1:36
 **/
public class Base64Utils {
    private static class Base64Encoderer extends BASE64Encoder {

    }

    private static class Base64Dncoderer extends BASE64Decoder {

    }

    /**
     * <p>Title:    .encrypt</p>
     * <p>Description 根据传入的字节数组进行Base64加密 </p>
     *
     * @param
     * @return String
     * @author 杨东旭
     * @CreateDate 2017/07/03
     */
    public static String encrypt(byte[] pic) {
        Base64Encoderer encoder = new Base64Encoderer();
        return encoder.encode(pic);
    }

    /**
     * <p>Title:    .decrypt</p>
     * <p>Description 根据传入的Bsdr64加密字符串进行Base64解密，并在对应的目录下输出（主要用于图片处理） </p>
     *
     * @param path,imagestr
     * @return void
     * @author 杨东旭
     * @CreateDate 2017/07/03
     */
    public static void decrypt(String path, String imagestr) {
        Base64Dncoderer decoder = new Base64Dncoderer();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imagestr);
            //开始输出
            for (int i = 0; i < bytes.length; ++i) {
                // 调整异常的数据
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(path);
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    /**
     * <p>Title:    .decrypt</p>
     * <p>Description 根据传入的字节数组，在对应的目录下输出（主要用于图片处理） </p>
     *
     * @param path,imagestr
     * @return void
     * @author 杨东旭
     * @CreateDate 2017/07/03
     */
    public static void decryptByByte(String path, byte[] data) {
        if (data.length < 3 || "".equals(path)) {
            return;
        }
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            //输出图片
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
    }
}
