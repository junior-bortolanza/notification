package com.bortolanza.notificacao.controller;

import com.bortolanza.notificacao.business.EmailService;
import com.bortolanza.notificacao.business.dto.TasksDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> sendEmail(@RequestBody TasksDTO dto) {
        emailService.sendEmail(dto);
        return ResponseEntity.ok().build();
    }
}
