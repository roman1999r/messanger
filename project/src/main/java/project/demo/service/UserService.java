package project.demo.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.entity.Role;
import project.demo.entity.User;
import project.demo.repository.RoleRepository;
import project.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService implements UserDetailsService {

    @Value("${file.path}")
    private  String path;

    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

//        user.setRole(Collections.singleton(new Role1(2L, "ROLE_USER")));
        user.setRole(Role.ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public String getUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        return username;
    }

    public User getLoginUser(){
        return userRepository.findByUsername(getUserName());
    }

    public void addAvatar(MultipartFile file) {
      //  User user = userRepository.findByUsername(getUserName());
        User user = getLoginUser();
        if(file!=null&&!file.isEmpty()) {
            String extention = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
            String fileName = UUID.randomUUID().toString()+extention;
            try {
                file.transferTo(new File(path+fileName));
                user.setPhotoUrl(fileName);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        userRepository.save(user);
    }


    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    };


}
