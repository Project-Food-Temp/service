package com.example.foodservice.data.service;

import com.example.foodservice.constants.RoleIdConstants;
import com.example.foodservice.constants.StatusRegisterUserEnum;
import com.example.foodservice.data.entity.Role;
import com.example.foodservice.data.entity.RoleUser;
import com.example.foodservice.data.entity.User;
import com.example.foodservice.data.repository.RoleRepository;
import com.example.foodservice.data.repository.RoleUserRepository;
import com.example.foodservice.data.repository.UserRepository;
import com.example.foodservice.ultis.bean.PayloadBean;
import com.example.foodservice.ultis.bo.RoleUserBean;
import com.example.foodservice.ultis.form.UserForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Value("${google.secretPsw}")
    private String secretGoogle;

//    @Autowired
//    private PermissionRoleRepository permissionRoleRepository;
    @Autowired
    private RoleUserService roleUserService;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *
     * @param user
     */
    public void addNewUser(User user) {
        userRepository.save(user);
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean updateUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean deleteUser(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }


    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     *
     * @param form
     * @return
     */
    public StatusRegisterUserEnum registerUser(UserForm form) {
        logger.info("Start Register");
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(form,User.class);
        try {
//            check existed username
            if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
                return StatusRegisterUserEnum.Existed_Username;
            }
//            check existed email
            if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
                return StatusRegisterUserEnum.Existed_Email;
            }

            user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
            user.setCreateDate(new Date());
            UUID uuid = UUID.randomUUID();
            String guid = uuid.toString();
            user.setGuid(guid);
            user.setCreateDate(new Date());
            user.setEnable(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);

            userRepository.save(user);

//            insert role user
            RoleUser userRole = new RoleUser();
            userRole.setRoleId(RoleIdConstants.ROLE_USER);
            userRole.setUserId(user.getId());
            roleUserRepository.save(userRole);
            return StatusRegisterUserEnum.Success;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return StatusRegisterUserEnum.Error_OnSystem;
        }
    }

    public User registerUserForGoogle(PayloadBean payloadBean) {
        logger.info("Start Register");
        User user = new User();
        try {
            user.setFirstName(payloadBean.getFamily_name());
            user.setLastName(payloadBean.getGiven_name());
            user.setUsername(payloadBean.getName());
            user.setEmail(payloadBean.getEmail());
            user.setPicture(payloadBean.getPicture());
            user.setPasswordHash(passwordEncoder.encode(secretGoogle));
            user.setCreateDate(new Date());
            user.setGuid(payloadBean.getSub());
            user.setEnable(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            userRepository.save(user);
//            insert role user
            RoleUser userRole = new RoleUser();
            userRole.setRoleId(RoleIdConstants.ROLE_USER);
            userRole.setUserId(user.getId());
            roleUserRepository.save(userRole);
            return user;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    public List<Role> getListRole() {
        return StreamSupport
                .stream(roleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Role> getRoleActiveOfUser(int id) {
        List<RoleUserBean> listUserRole = roleUserService.findRoleOfUser(id);

        return getListRole()
                .stream()
                .filter(permission -> {
                    return (listUserRole.stream()
                            .filter(userRole -> userRole.getRoleId() == permission.getId())
                            .findFirst().orElse(null) != null);
                }).collect(Collectors.toList());
    }
}
