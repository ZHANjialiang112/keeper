package com.zjl.keeper.api.login;

import com.zjl.keeper.core.context.User;
import com.zjl.keeper.core.context.UserContextHolder;
import com.zjl.keeper.core.jwt.EncryptService;
import com.zjl.keeper.domain.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenman
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final EncryptService encryptService;
    private final TeacherService teacherService;

    @GetMapping("/user-login")
    public String userLogin() {
        return encryptService.createToken(new User("admin", "admin"));
    }

    @GetMapping("/admin-login")
    public String adminLogin() {
        String s = teacherService.haveLog();
        log.info("adminLogin{}", s);
        return "CONTEXT-USER===" + UserContextHolder.getUserName();
    }
}
