package com.example.foodservice.common;

import com.example.foodservice.domain.DataTableResults;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Nhan Nguyen on 5/19/2021
 *
 * @author Nhan Nguyen
 * @date 5/19/2021
 */
public interface VfData {
    public <T> DataTableResults<T>
    findPaginationQuery(String nativeQuery, String nativeQueryCount,
                        String orderBy, List<Object> paramList, Class obj);

    /**
     * @param findPaginationQuery
     * @param paramList
     * @param obj
     * @return
     */
    public <T> DataTableResults<T> findPaginationQuery(String nativeQuery, String orderBy, List<Object> paramList, Class obj);
//    public <T> DataTableResults<T> findPaginationQuery(String nativeQuery, String orderBy, Map<String, String> paramList, Class obj);

    public List findAllByQuery(String nativeQuery, Class obj);

    /**
     * Get session.
     */
    public Session getSession();

    public SQLQuery createSQLQuery(String sql);

    /**
     * ham set result transformer cua cau query
     *
     * @param query cau query
     * @param obj   doi tuong
     */
    public void setResultTransformer(SQLQuery query, Class obj);

    /**
     * Get list alias column.
     *
     * @param query
     * @return
     */
    public List<String> getReturnAliasColumns(SQLQuery query);
}
