package br.com.viniciusmarlin.library.controller;

import br.com.viniciusmarlin.library.dto.LoanDTO;
import br.com.viniciusmarlin.library.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanDTO.CreateLoanDTO> createLoan(
            @RequestParam UUID userId,
            @RequestParam UUID bookId
    ) {

        LoanDTO.CreateLoanDTO response =
                loanService.createLoan(userId, bookId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{loanId}/return")
    public ResponseEntity<Void> returnLoan(
            @PathVariable UUID loanId, UUID bookId
    ) {

        loanService.returnLoan(loanId, bookId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public List<LoanDTO.LoanResponseDTO> listAll(@PathVariable UUID userId) {
        return loanService.findLoansByUser(userId);
    }

    @GetMapping("/user/{userId}/active")
    public List<LoanDTO.LoanResponseDTO> listAllActive(@PathVariable UUID userId) {
        return loanService.findActiveLoansByUser(userId);
    }

    @GetMapping("/user/{userId}/late")
    public List<LoanDTO.LoanResponseDTO> listAllLate(@PathVariable UUID userId) {
        return loanService.findLateLoansByUser(userId);
    }
}