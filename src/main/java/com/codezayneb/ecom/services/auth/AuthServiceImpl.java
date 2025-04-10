package com.codezayneb.ecom.services.auth;

import com.codezayneb.ecom.dto.SignupRequest;
import com.codezayneb.ecom.dto.UserDto;
import com.codezayneb.ecom.entity.Order;
import com.codezayneb.ecom.entity.User;
import com.codezayneb.ecom.enums.OrderStatus;
import com.codezayneb.ecom.enums.UserRole;
import com.codezayneb.ecom.repository.OrderRepository;
import com.codezayneb.ecom.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    //private BCryptPasswordEncoder bCryptPasswordEncoder;
    private PasswordEncoder passwordEncoder;


    @Autowired
    private OrderRepository orderRepository;

    public UserDto createUser(SignupRequest signupRequest){

        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        User createUser =userRepository.save(user);

        Order order = new Order();

        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(createUser);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepository.save(order);


        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId());

        return userDto;
    }

    @Override
    public boolean hasUserWithEmail(String email) {

        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (null == adminAccount) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode( "admin"));
            userRepository.save(user);
        }
    }

}
