package cn.com.boke.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:	  Snippet <br/> </p>
 * <p>Description 公用工具类 <br/> </p>
 *
 * @Author <a href="zhaoming.liu@fmscm.com"/>刘兆明</a>  <br/>
 * @CreateDate 2015年9月19日 下午2:56:32 <br/>
 */

public class PublicUtil {
    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if (pObj == "")
            return true;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素>0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null)
            return false;
        if (pObj == "")
            return false;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return false;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return false;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Title: splitList
     * <p>
     * Description:拆分list，按500条拆分
     *
     * @param <T>
     * @param list
     * @return
     */
    public static <T> List<List<T>> separateList(List<T> list) {
        List<List<T>> lists = new ArrayList<List<T>>();
        List<T> subList;
        int size = list.size();
        int sum = 100;
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

}
