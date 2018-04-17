package cn.com.boke.service.impl;

import cn.com.boke.service.IService;
import cn.com.boke.utils.PubUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseService<T> implements IService<T> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public List<T> select(T record) {
        return mapper.select(record);
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public T selectOne(T record) {
        return mapper.selectOne(record);
    }

    @Override
    public int selectCount(T record) {
        return mapper.selectCount(record);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int save(T record) {
        int result;
        try {
            result = mapper.insertSelective(record);
        } catch (DuplicateKeyException e) {
            return 0;
        }
        return result;
    }

    @Override
    public int update(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int delete(T record) {
        return mapper.delete(record);
    }

    @Override
    public int deleteByKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int batchDelete(List<T> list) {
        int result = 0;
        for (T record : list) {
            int count = mapper.delete(record);
            if (count < 1) {
                throw new RuntimeException("插入数据失败!");
            }
            result += count;
        }
        return result;
    }

    @Override
    public int selectCountByExample(Object example) {
        mapper.selectCountByExample(example);
        return 0;
    }

    @Override
    public int updateByExample(T record, Object example) {
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int deleteByExample(Object example) {
        return mapper.deleteByPrimaryKey(example);
    }

    @Override
    public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
        return mapper.selectByRowBounds(record, rowBounds);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        return mapper.selectByExampleAndRowBounds(example, rowBounds);
    }

    @Override
    public List<T> selectByDistExampleSortForClass(boolean ifDistinct, String sort, T entity) {
        List<T> list = null;
        try {
            Class newClass = entity.getClass();
            Example myExample = new Example(newClass);
            if (!PubUtils.trimAndNullAsEmpty(sort).equals("")) {
                myExample.setOrderByClause(sort);
            }
            Example.Criteria criteria = myExample.createCriteria();
            Field[] fields = newClass.getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
            for (Field field : fields) {
                getSetValue(entity, field.getName(), criteria);
            }
            list = selectByExample(myExample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void getSetValue(T entity, String fieldName, Example.Criteria criteria) {
        Method m;
        try {
            m = entity.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
            Object value = m.invoke(entity);// 调用getter方法获取属性值
            if (value != null) {
                criteria.andEqualTo(fieldName, value);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private String getCurrentClassName(T record) {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass.toString().substring(entityClass.toString().lastIndexOf(".") + 1);
    }
}
