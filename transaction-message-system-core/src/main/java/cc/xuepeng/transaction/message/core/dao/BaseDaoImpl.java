package cc.xuepeng.transaction.message.core.dao;

import cc.xuepeng.transaction.message.common.entity.BaseEntity;
import cc.xuepeng.transaction.message.common.entity.PageDataEntity;
import cc.xuepeng.transaction.message.common.entity.PageParamEntity;
import cc.xuepeng.transaction.message.core.dao.interceptor.ExecutorInterceptor;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据访问类的基础支撑泛型类。
 *
 * @param <T> 继承自BaseEntity的实体类。
 * @author xuepeng
 */
public abstract class BaseDaoImpl<T extends BaseEntity> extends SqlSessionDaoSupport implements BaseDao<T> {

    /**
     * 新增。
     */
    private static final String SQL_INSERT = "insert";
    /**
     * 修改。
     */
    private static final String SQL_UPDATE = "update";
    /**
     * 根据主键删除。
     */
    private static final String SQL_DELETE_BY_ID = "deleteById";
    /**
     * 根据主键批量删除。
     */
    private static final String SQL_DELETE_BY_IDS = "deleteByIds";
    /**
     * 根据主键查找。
     */
    private static final String SQL_FIND_BY_ID = "findById";
    /**
     * 根据参数查找。
     */
    private static final String SQL_FIND_BY_PARAM = "findByParam";
    /**
     * 根据参数查找多个。
     */
    private static final String SQL_LIST_BY_PARAM = "listByParam";
    /**
     * 根据参数分页查找多个。
     */
    private static final String SQL_LIST_BY_PAGE = "listByPageAndParam";
    /**
     * 根据参数查找数量。
     */
    private static final String SQL_COUNT_BY_PARAM = "countByParam";

    // SqlSessionTemplate实例(要求Spring中进行SqlSessionTemplate的配置)
    // 可以调用sessionTemplate完成数据库操作
    private SqlSessionTemplate sessionTemplate;

    // SqlSessionFactory实例(要求Spring中进行SqlSessionFactory的配置)
    // 可以调用sessionFactory打开一个SqlSession
    private SqlSessionFactory sessionFactory;

    /**
     * @return 获得一个SqlSessionTemplate对象。
     */
    public SqlSessionTemplate getSessionTemplate() {
        return this.sessionTemplate;
    }

    /**
     * 注入SqlSessionTemplate对象。
     */
    @Autowired
    public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
        super.setSqlSessionTemplate(sessionTemplate);
        this.sessionTemplate = sessionTemplate;
    }

    /**
     * @return 获得SqlSessionFactory对象。
     */
    public SqlSessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    /**
     * 注入SqlSessionFactory对象。
     */
    @Override
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sessionFactory) {
        super.setSqlSessionFactory(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    /**
     * 新建实体对象。
     *
     * @param entity 实体对象。
     * @return 新建成功的数量。
     */
    @Override
    public int insert(T entity) {
        // 对数据库进行insert操作。
        // 执行Mapper配置文件中的insert方法。
        return sessionTemplate.insert(getStatement(SQL_INSERT), entity);
    }

    /**
     * 修改实体对象。
     *
     * @param entity 实体对象。
     * @return 编辑成功的数量。
     */
    @Override
    public int update(T entity) {
        // 对数据库进行update操作，并返回影响行数。
        // 执行Mapper配置文件中的update方法。
        return sessionTemplate.update(getStatement(SQL_UPDATE), entity);
    }

    /**
     * 根据主键删除实体对象。
     *
     * @param id 实体对象的主键。
     * @return 删除成功的数量。
     */
    @Override
    public int deleteById(String id) {
        // 对数据库进行删除操作。
        // 执行Mapper配置文件中的deleteById方法。
        return sessionTemplate.delete(getStatement(SQL_DELETE_BY_ID), id);
    }

    @Override
    public int deleteByIds(String... ids) {
        return sessionTemplate.delete(getStatement(SQL_DELETE_BY_IDS), ids);
    }

    /**
     * 根据主键查找实体对象。
     *
     * @param id 实体对象的主键。
     * @return 实体对象。
     */
    @Override
    public T findById(String id) {
        // 对数据库进行查询操作。
        // 执行Mapper配置文件中的findById方法。
        return sessionTemplate.selectOne(getStatement(SQL_FIND_BY_ID), id);
    }

    /**
     * 根据查询条件来查找实体对象。
     *
     * @param map 查询条件集合。
     * @return 实体对象。
     */
    @Override
    public T findByParam(Map<String, Object> map) {
        // 对数据库进行查询操作。
        // 执行Mapper配置文件中的findByParam方法。
        if (map == null || map.isEmpty()) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_FIND_BY_PARAM), map);
    }

    /**
     * 根据查询条件来查找实体对象集合。
     *
     * @param map 查询条件集合。
     * @return 实体对象的集合。
     */
    @Override
    public List<T> listByParam(Map<String, Object> map) {
        // 对数据库进行查询操作。
        // 执行Mapper配置文件中的findListByParam方法。
        return sessionTemplate.selectList(getStatement(SQL_LIST_BY_PARAM), map);
    }

    /**
     * 根据分页和条件查找实体对象集合。
     *
     * @param page 分页对象。
     * @param map  查询条件集合。
     * @return 实体对象的集合。
     */
    @Override
    public PageDataEntity<T> listByPageAndParam(PageParamEntity page, Map<String, Object> map) {
        return listByPageAndParam(page, map, SQL_LIST_BY_PAGE);
    }

    /**
     * 根据分页和条件查找实体对象集合。
     *
     * @param page  分页对象。
     * @param map   查询条件集合。
     * @param sqlId 查询函数名称。
     * @return 实体对象的集合。
     */
    public PageDataEntity<T> listByPageAndParam(PageParamEntity page, Map<String, Object> map, String sqlId) {
        // 对数据库进行查询操作，执行Mapper配置文件中的自定义的方法。
        Map<String, Object> params;
        if (map == null) {
            params = new HashMap<>();
        } else {
            params = map;
        }
        // 获取分页数据集 , 注切勿换成 sessionTemplate 对象
        List<T> list = getSqlSession().selectList(getStatement(sqlId), params,
                new RowBounds((page.getPageNum() - 1) * page.getNumPerPage(), page.getNumPerPage()));
        // 统计总记录数
        Object countObject = getSqlSession().selectOne(getStatement(sqlId),
                new ExecutorInterceptor.CountParameter(params));
        Long count = Long.valueOf(countObject.toString());

        return new PageDataEntity<>(page.getPageNum(), page.getNumPerPage(), count.intValue(), list);
    }

    /**
     * 根据查询条件查询数据库中的记录数。
     *
     * @param map 查询条件集合。
     * @return 数据库中的记录数。
     */
    @Override
    public long countByParam(Map<String, Object> map) {
        // 对数据库进行查询操作。
        // 执行Mapper配置文件中的findCountByParam方法。
        return sessionTemplate.selectOne(getStatement(SQL_COUNT_BY_PARAM), map);
    }

    /**
     * 通过sqlId创建全路径名的字符串。
     * 通过当前类的getName()获得类的全名，拼接sqlId组成全路径方法名。
     *
     * @param sqlId 要执行的方法名称。
     * @return 全路径的方法名称。
     */
    protected String getStatement(String sqlId) {
        String name = this.getClass().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(".").append(sqlId);
        return sb.toString();
    }

}
