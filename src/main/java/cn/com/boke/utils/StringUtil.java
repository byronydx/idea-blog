package cn.com.boke.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.*;

/**
 * 字符串工具类，继续自 org.apache.commons.lang.StringUtils
 *
 * <h1>API : <font color="#FF0000">http://commons.apache.org/proper/commons-lang/javadocs/api-release/index.html</font></h1>
 * <p>http://blog.csdn.net/chinarenzhou/article/details/4090499
 */
public class StringUtil extends StringUtils {
    private static final Log log = LogFactory.getLog(StringUtil.class);
    private static Properties dbconfig;

    public static String getDBUserName() {
        if (dbconfig == null) {
            try {
                dbconfig = PropertiesLoaderUtils.loadAllProperties("important.properties");
            } catch (IOException e) {
                log.error("important.properties初始化失败" + e.toString(), e);
                e.printStackTrace();
            }
        }
        return dbconfig.getProperty("bosDataSource.db.name") == null ? "" : dbconfig.getProperty("bosDataSource.db.name").toString() + ".";
    }

    /**
     * 获取本地ip地址
     * Title: getLocalIP
     * <p>
     * Description:
     *
     * @return
     */
    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取32位随机码
     * Title: getUUID
     * <p>
     * Description:
     *
     * @return
     */
    public static String getUUID() {
        try {
            return (UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 取摸的条件
     * Title: getCondition
     * <p>
     * Description:将服务器实例列表转为以逗号间隔的字符串
     *
     * @param queueList
     * @return
     */
    public static StringBuffer getCondition(List<?> queueList) {
        StringBuffer condition = new StringBuffer();
        for (int i = 0; i < queueList.size(); i++) {
            if (i > 0) {
                condition.append(",");
            }
            condition.append(queueList.get(i));
        }
        return condition;
    }

    /**
     * 判断一个或多个对象是否为空
     *
     * @param values 可变参数，要判断的一个或多个对象
     * @return 只有要判断的一个对象都为空则返回true, 否则返回false
     */
    public static boolean isNull(Object... values) {
        if (isNotNullAndNotEmpty(values)) {
            return false;
        }
        for (Object value : values) {
            boolean flag = false;
            if (value instanceof Object[]) {
                flag = !isNotNullAndNotEmpty((Object[]) value);
            } else if (value instanceof Collection<?>) {
                flag = !isNotNullAndNotEmpty((Collection<?>) value);
            } else {
                flag = (null == value);
            }
            if (flag) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断一个或多个对象是否为非空
     *
     * @param values 可变参数，要判断的一个或多个对象
     * @return 只有要判断的一个或多个对象都不为空则返回true, 否则返回false
     */
    public static boolean isNotNull(Object... values) {
        if (!isNotNullAndNotEmpty(values)) {
            return false;
        }
        for (Object value : values) {
            boolean flag = true;
            if (value instanceof Object[]) {
                flag = isNotNullAndNotEmpty((Object[]) value);
            } else if (value instanceof Collection<?>) {
                flag = isNotNullAndNotEmpty((Collection<?>) value);
            } else {
                flag = (null != value);
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断对象数组是否为空并且数量大于0
     *
     * @param value
     * @return
     */
    public static Boolean isNotNullAndNotEmpty(Object[] value) {
        boolean bl = false;
        if (null != value && 0 < value.length) {
            bl = true;
        }
        return bl;
    }

    /**
     * 判断对象集合（List,Set）是否为空并且数量大于0
     *
     * @param value
     * @return
     */
    public static Boolean isNotNullAndNotEmpty(Collection<?> value) {
        boolean bl = false;
        if (null != value && 0 < value.size()) {
            bl = true;
        }
        return bl;
    }

    /**
     * Title: splitList
     * <p>
     * Description:拆分list，按500条拆分
     *
     * @param list
     * @return
     */
    public static List<List<String>> splitList(List<String> list) {
        List<List<String>> lists = new ArrayList<List<String>>();
        List<String> subList = new ArrayList<String>();
        int size = list.size();
        int sum = 500;
        int count = size / sum;
        int yu = size % sum;
        if (count == 0) {
            lists.add(list);
        } else {
            if (size % sum != 0) {
                count++;
            }
            for (int i = 0; i < count; i++) {
                if (sum * (i + 1) <= size) {
                    subList = list.subList(sum * i, sum * (i + 1));
                } else {
                    subList = list.subList(sum * i, sum * (i) + yu);
                }
                lists.add(subList);
            }
        }
        return lists;

    }

    /**
     * 判断对象中的属性是否都为空
     *
     * @param obj 对象
     * @return true : 对象中所有数据为空 false: 对象中所有对象不是都为空
     * @Auto : BOBO
     */
    public static boolean dtoIsNull(Object obj) throws Exception {

        if (StringUtils.isEmpty(obj)) {
            return true;
        }

        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(obj) != null) { //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
                return false;
            }
        }

        return true;

    }
}
