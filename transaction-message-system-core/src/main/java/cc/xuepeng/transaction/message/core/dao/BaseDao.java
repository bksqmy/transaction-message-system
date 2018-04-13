package cc.xuepeng.transaction.message.core.dao;

import cc.xuepeng.transaction.message.common.entity.BaseEntity;
import cc.xuepeng.transaction.message.common.entity.PageDataEntity;
import cc.xuepeng.transaction.message.common.entity.PageParamEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据访问类的基础支撑泛型接口。
 *
 * @param <T> 继承自BaseEntity的实体类。
 * @author xuepeng
 */
public interface BaseDao<T extends BaseEntity> {

    /**
     * 新建实体对象。
     *
     * @param entity 实体对象。
     * @return 新建成功的数量。
     */
    int insert(T entity);

    /**
     * 修改实体对象。
     *
     * @param entity 实体对象。
     * @return 编辑成功的数量。
     */
    int update(T entity);

    /**
     * 根据主键删除实体对象。
     *
     * @param id 实体对象的主键。
     * @return 删除成功的数量。
     */
    int deleteById(String id);

    /**
     * 根据主键删除实体对象。
     *
     * @param id 实体对象的主键。
     * @return 删除成功的数量。
     */
    int deleteByIds(String... id);

    /**
     * 根据主键查找实体对象。
     *
     * @param id 实体对象的主键。
     * @return 实体对象。
     */
    T findById(String id);

    /**
     * 根据查询条件来查找实体对象。
     *
     * @param map 查询条件集合。
     * @return 实体对象。
     */
    T findByParam(Map<String, Object> map);

    /**
     * 根据查询条件来查找实体对象集合。
     *
     * @param map 查询条件集合。
     * @return 实体对象的集合。
     */
    List<T> listByParam(Map<String, Object> map);

    /**
     * 根据分页和条件查找实体对象集合。
     *
     * @param page 分页对象。
     * @param map  查询条件集合。
     * @return 实体对象的集合。
     */
    PageDataEntity<T> listByPageAndParam(PageParamEntity page, Map<String, Object> map);

    /**
     * 根据分页和条件查找实体对象集合。
     *
     * @param page  分页对象。
     * @param map   查询条件集合。
     * @param sqlId 查询函数名称。
     * @return 实体对象的集合。
     */
    PageDataEntity<T> listByPageAndParam(PageParamEntity page, Map<String, Object> map, String sqlId);

    /**
     * 根据查询条件查询数据库中的记录数。
     *
     * @param map 查询条件集合。
     * @return 数据库中的记录数。
     */
    long countByParam(Map<String, Object> map);

}
