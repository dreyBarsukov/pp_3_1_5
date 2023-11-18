package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void save(User user, List<String> roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(roleRepository.findByName(role));
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setRoles(roleSet);
        userRepository.save(user);
    }

    @Transactional
    public void update(long id, User updateUser, List<String> roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(roleRepository.findByName(role));
        }
        String encode = passwordEncoder.encode(updateUser.getPassword());
        updateUser.setRoles(roleSet);
        updateUser.setPassword(encode);
        updateUser.setId(id);
        userRepository.save(updateUser);
    }

    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    //    private UserDao userDao;
//    private RoleService roleService;
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleService roleService) {
//        this.userDao = userDao;
//        this.roleService = roleService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public UserServiceImpl() {
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public List<User> findAll() {
//        return userDao.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public User findOne(long id) {
//        return userDao.findOne(id);
//    }
//
//    @Transactional
//    @Override
//    public void save(User user, List<String> roles) {
//        Set<Role> roleSet = new HashSet<>();
//        for (String role : roles) {
//            roleSet.add(roleService.findByName(role));
//        }
//        String encode = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encode);
//        user.setRoles(roleSet);
//        userDao.save(user);
//    }
//
//    @Transactional
//    @Override
//    public void update(long id, User updeteUser, List<String> roles) {
//        Set<Role> roleSet = new HashSet<>();
//        for (String role : roles) {
//            roleSet.add(roleService.findByName(role));
//        }
//        String encode = passwordEncoder.encode(updeteUser.getPassword());
//        updeteUser.setRoles(roleSet);
//        updeteUser.setPassword(encode);
//        userDao.update(id, updeteUser);
//    }
//
//    @Transactional
//    @Override
//    public void delete(long id) {
//        userDao.delete(id);
//    }
}
