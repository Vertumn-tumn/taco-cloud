package tacos.web;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.RegistrationForm;
import tacos.data.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder encoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping
    public String registerForm() {
        return "register";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userRepository.save(form.toUser(encoder));
        return "redirect:/login";
    }
}
