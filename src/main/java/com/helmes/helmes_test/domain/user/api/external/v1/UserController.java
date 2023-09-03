package com.helmes.helmes_test.domain.user.api.external.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helmes.helmes_test.domain.sector.service.SectorService;
import com.helmes.helmes_test.domain.user.model.User;
import com.helmes.helmes_test.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final SectorService sectorService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public String index(Model model) {
        var sectors = sectorService.getAllFlattenedWithIndentation();

        if (model.getAttribute("user") == null) {
            model.addAttribute("user", new User());
        }
        model.addAttribute("sectors", sectors);

        return "index";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") @Validated User user, RedirectAttributes redirectAttributes) {
        user.setAgreedToTerms(Instant.now());
        redirectAttributes.addFlashAttribute("user", userRepository.save(user));

        return "redirect:/";
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handler(MethodArgumentNotValidException e, HttpServletResponse response) throws IOException {
        var message = e
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(APPLICATION_JSON_VALUE);

        return objectMapper.writeValueAsString(message);
    }
}
