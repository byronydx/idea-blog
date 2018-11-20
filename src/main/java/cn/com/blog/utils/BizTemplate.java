/*
 * Copyright (c) 2013 Qunar.com. All Rights Reserved.
 */
package cn.com.blog.utils;

import cn.com.blog.exception.BusinessException;
import cn.com.blog.wrap.WrapMapper;
import cn.com.blog.wrap.Wrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Title:  BizTemplate </p>
 * <p>Description 处理器 </p>
 * <p>Company:    yunshanmeicai </p>
 *
 * @author 杨东旭
 */
@Slf4j
public abstract class BizTemplate<T> {

    protected BizTemplate() {
    }

    protected abstract void checkParams();

    protected abstract T process();

    protected void afterProcess() {
    }

    protected void onSuccess() {
    }

    protected void onBusinessException(BusinessException e) {
        log.warn("执行业务逻辑出现异常 errorCode={} , msg:{}", e.getCode(), e.getMessage(), e);
        throw e;
    }

    protected void onError(Throwable e) {
        log.error("执行业务逻辑出现未知异常 errorCode={}", 500, e);
        throw new BusinessException("500", "网络繁忙，请稍后重试~!");
    }

    protected void onNpeException(NullPointerException e) {
        log.error("作为程序员的基本修养，NPE其实是不应该出现的，但既然出现了就追查到底吧。给点提示：", e);
        throw new BusinessException("500", "空指针异常");
    }

    public Wrapper<T> execute() {
        try {
            checkParams();
        } catch (IllegalArgumentException e) {
            if (log.isDebugEnabled()) {
                log.debug("校验参数失败", e);
            } else {
                log.info("校验参数失败: " + e.getMessage());
            }
            throw e;
        } catch (BusinessException e) {
            log.info("校验参数失败: errorCode={} , msg:{}", e.getCode(), e.getMessage());
            throw e;
        }
        try {
            T result = process();
            onSuccess();
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
        } catch (BusinessException e) {
            onBusinessException(e);
            return null;
        } catch (NullPointerException e) {
            onNpeException(e);
            return null;
        } catch (Throwable e) {
            onError(e);
            return null;
        } finally {
            afterProcess();
        }
    }

}
