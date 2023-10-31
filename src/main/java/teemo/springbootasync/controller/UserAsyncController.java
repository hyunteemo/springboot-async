package teemo.springbootasync.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teemo.springbootasync.model.UserDto;
import teemo.springbootasync.service.UserService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("userAsync")
public class UserAsyncController {

    private final static Logger LOG = LoggerFactory.getLogger(UserAsyncController.class);

    @Autowired
    UserService userService;

    public CompletableFuture<List<UserDto>> getUserByName(@PathVariable String firstName) {
        return userService.getUserByNameAsync(firstName);
    }


}
