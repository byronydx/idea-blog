package cn.com.ydxboke.utils;

import org.springframework.stereotype.Component;

/**
 * Created by 杨东旭 on 2017/6/8.
 */
@Component
public class RedisDistributLockUtils {
    /*private Logger logger = LoggerFactory.getLogger(RedisDistributLockUtils.class);
    @Resource
    private JedisPool jedisPool;

    private int addLock(String key, int lockSecond) {
        logger.info("addLock - 加分布式锁. key = {}, lockSecond = {}", key, lockSecond);
        int resultInt = 0;

        try {
            resultInt = this.setLock(key, lockSecond);
        } catch (Exception var5) {
            logger.error("addLock - 加分布式锁失败. key = {}, lockSecond = {}", key, lockSecond);
        }

        return resultInt;
    }

    private int setLock(String key, int lockSecond) {
        Jedis jedis = null;
        int result = 0;
        long nowMillis = System.currentTimeMillis();
        long lockEndMill = nowMillis + (long)(lockSecond * 1000);
        String lockEndMillStr = String.valueOf(lockEndMill);

        try {
            jedis = getResource();
            if(jedis.setnx(key, lockEndMillStr) == 0L) {
                String endMill = jedis.get(key);
                if(endMill != null && System.currentTimeMillis() <= Long.parseLong(endMill)) {
                    result = -1;
                } else {
                    String oldMill = jedis.getSet(key, lockEndMillStr);
                    if(oldMill != null && !oldMill.equals(endMill)) {
                        result = -2;
                    } else {
                        result = 1;
                        jedis.expire(key, lockSecond);
                    }
                }
            } else {
                result = 1;
                jedis.expire(key, lockSecond);
            }
        } catch (Exception var15) {
            logger.error(var15.getMessage(), var15);
        } finally {
            this.close(jedis);
        }

        return result;
    }

    private Long clearLock(String... key) {
        logger.info("clearLock - 清空分布式锁. key = {}", key);
        Long resultLong = 0L;

        try {
            if(PublicUtil.isEmpty(key)) {
                throw new BusinessException("key is empty");
            }

            resultLong = this.delKey(key);
            logger.info("clearLock - 清空分布式锁. key = {}, [OK]", key);
        } catch (Exception var4) {
            logger.error("clearLock - 清空分布式锁. key = {}, [FAIL]", key);
        }

        return resultLong;
    }

    public int batchAddLock(String[] keys, int lockSecond) {
        logger.info("batchAddLock - 批量加分布式锁. key = {}, lockSecond = {}", keys, lockSecond);
        byte resultInt = 1;

        try {
            if(PublicUtil.isEmpty(keys)) {
                throw new BusinessException("keys is empty");
            }

            List<String> lockList = Lists.newArrayList();

            for (String key : keys) {
                if (this.addLock(key, lockSecond) != 1) {
                    logger.error("batchAddLock - 批量加分布式锁失败，Key={}, lockSecond={}, [FAIL]", key, lockSecond);
                    if (lockList.size() > 0) {
                        this.clearLock((String[]) lockList.toArray(new String[lockList.size()]));
                    }

                    resultInt = -1;
                    break;
                }

                lockList.add(key);
                logger.info("batchAddLock - 批量加分布式锁，Key={}, lockSecond={}, [OK]", key, lockSecond);
            }
        } catch (Exception var9) {
            logger.error("batchAddLock - 批量加分布式锁. key = {}, lockSecond = {}", keys, lockSecond, var9);
        }

        return resultInt;
    }

    public String checkLocksExist(String keys) {
        logger.info("checkLocksExist - 查看锁存在情况. key = {}");
        String result = null;

        try {
            StringBuilder sb = new StringBuilder();
            if(keys != null && keys.trim().length() > 0) {
                String[] strKeys = keys.split(",");

                for (String key : strKeys) {
                    String endMill = this.get(key);
                    if (endMill != null && System.currentTimeMillis() <= Long.parseLong(endMill)) {
                        sb.append(",");
                        sb.append(key);
                    } else {
                        logger.error("checkLocksExist - 查看锁存在情况. key = {}已过期");
                    }
                }

                if(sb.length() > 0) {
                    result = sb.toString();
                    result = result.replaceFirst(",", "");
                }
            }
        } catch (Exception var10) {
            logger.error("checkLocksExist - 查看锁存在情况. key = {}", keys, var10);
        }

        return result;
    }

    public Jedis getResource() {
        return this.jedisPool.getResource();
    }

    private void close(Jedis jedis) {
        logger.info("close - 关闭Jedis链接 jedis = {}", jedis);

        try {
            if(jedis != null) {
                jedis.close();
            }
        } catch (Exception var3) {
            logger.info("close - 关闭Jedis链接失败", var3);
        }

    }

    public void set(String key, String value) {
        logger.info("get - 设置缓存，Key={}, Value={}", key, value);
        Jedis jedis = null;

        try {
            if(PublicUtil.isEmpty(key)) {
                throw new BusinessException("keys is empty");
            }

            jedis = this.getResource();
            jedis.set(key, value);
            logger.info("get - 设置缓存，Key={}, Value={}, [OK]", key, value);
        } catch (Exception var8) {
            var8.printStackTrace();
            logger.error("get - 设置缓存，Key={}, Value={}, [FAIL]", key, value, var8);
        } finally {
            this.close(jedis);
        }

    }

    public String get(String key) {
        logger.info("get - 读取缓存，Key={}", key);
        String result = null;
        Jedis jedis = null;

        try {
            if(PublicUtil.isEmpty(key)) {
                throw new BusinessException("keys is empty");
            }

            jedis = this.getResource();
            result = jedis.get(key);
            logger.info("get - 读取缓存，Key={}, result = {}, [OK]", key, result);
        } catch (Exception var8) {
            logger.error("get - 读取缓存，Key={}, result = {}, [FAIL]", key, result, var8);
        } finally {
            this.close(jedis);
        }

        return result;
    }

    private long delKey(String... key) {
        Jedis jedis = null;
        long len = 0L;

        try {
            if(PublicUtil.isEmpty(key)) {
                throw new BusinessException("key is empty");
            }

            logger.info("delKey - 清空缓存，Key={}", (Object) key);
            jedis = this.getResource();
            len = jedis.del(key);
            logger.info("delKey - 清空缓存，Key={}, [OK]", (Object) key);
        } catch (Exception var9) {
            logger.info("delKey - 清空缓存失败，Key={}, [FAIL]", key, var9);
        } finally {
            this.close(jedis);
        }

        return len;
    }*/
}
