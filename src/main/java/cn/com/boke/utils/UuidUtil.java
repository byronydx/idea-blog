package cn.com.boke.utils;

import java.util.UUID;

/**
 * UUID
 * Created by hiyond on 2016/12/5.
 */
public class UuidUtil {

    /**
     * 生成UUID
     * @return uuid
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
