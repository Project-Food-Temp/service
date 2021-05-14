package com.example.foodservice.data.service;

import com.example.foodservice.data.repository.RoleUserRepository;
import com.example.foodservice.ultis.bean.RoleUserBean;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by NhanNguyen on 4/28/2021
 *
 * @author: NhanNguyen
 * @date: 4/28/2021
 */
@Service
public class RoleUserService {
    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private EntityManager entityManager;

    public List<RoleUserBean> findRoleOfUser(int idUser){
        StringBuilder strQuery = new StringBuilder("select ");
        strQuery.append("   ru.id as id, ");
        strQuery.append("   ru.role_id as roleId, ");
        strQuery.append("   ru.user_id as userId, ");
        strQuery.append("   from role_user ru ");
        strQuery.append("   order by ru.id DESC");
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery(strQuery.toString());
        return query.getResultList();
    }
}
