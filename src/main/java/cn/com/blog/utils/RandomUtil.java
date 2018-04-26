package cn.com.blog.utils;

import java.util.Random;

/**
 * <p>Title:	  RandomUtil. </p>
 * <p>Description 随机数工具类 </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
 * @CreateDate 2016/12/5 15:29
 */
public class RandomUtil {

    /**
     * 生成一个随机验证码 大小写字母+数字
     *
     * @return 随机验证码
     */
    public static String createComplexCode(int length) {

        if (length > 50) {
            length = 50;
        }

        Random r = new Random();

        String code = "";

        while (true) {
            if (code.length() == length) {
                break;
            }
            int tmp = r.nextInt(127);
            if (tmp < 33 || tmp == 92 || tmp == 47 || tmp == 34) {
                continue;
            }
            char x = (char) (tmp);
            if (code.indexOf(x) > 0) {
                continue;
            }
            code += x;
        }

        return code;
    }
}
