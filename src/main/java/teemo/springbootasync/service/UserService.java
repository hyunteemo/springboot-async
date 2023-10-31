package teemo.springbootasync.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teemo.springbootasync.domain.User;
import teemo.springbootasync.model.UserDto;
import teemo.springbootasync.repo.UserRepository;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final static Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepo;

    public List<UserDto> getUserByName(String firstName) {

        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userRepo.findAllByFirstName(firstName);
        for(User u: users) {
            userDtos.add(userToUserDto(u));
        }
        return userDtos;

    }

    public CompletableFuture<List<UserDto>> getUserByNameAsync(String firstName) {
        CompletableFuture<List<User>> users = userRepo.findAllByLastName(firstName);
        return users.thenApply( urs -> {
            return urs.stream().map(this::userToUserDto).collect(Collectors.toList());
        });
    }

    public UserDto createUser(UserDto u) {
        User user = new User();
        user.setEmailId(u.getEmailId());
        user.setPhoneNbr(u.getPhoneNbr());
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user = userRepo.save(user);
        return userToUserDto(user);
    }

    public UserDto getUserById(Integer id) {
        Optional<User> userOpt = userRepo.findById(id);
        UserDto u = new UserDto();

        if (userOpt.isPresent()) {
            u = userToUserDto(userOpt.get());
        }

        return u;
    }
    private UserDto userToUserDto(User u) {

        UserDto userDto = new UserDto();
        userDto.setEmailId(u.getEmailId());
        userDto.setPhoneNbr(u.getPhoneNbr());
        userDto.setFirstName(u.getFirstName());
        userDto.setLastName(u.getLastName());
        userDto.setUserId(u.getUserId());

        return userDto;
    }
}
