package com.zjl.keeper.api.login;

import com.zjl.keeper.core.context.User;
import com.zjl.keeper.core.context.UserContextHolder;
import com.zjl.keeper.core.jwt.EncryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenman
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final EncryptService encryptService;

    @GetMapping("/user-login")
    public String userLogin() {
        return encryptService.createToken(new User("admin", "admin"));
    }

    @GetMapping("/admin-login")
    public String adminLogin() {
        return "CONTEXT-USER===" + UserContextHolder.getUserName();
    }
}
