package hu.sajat.springgyakorlas.controller;

import hu.sajat.springgyakorlas.dto.RegisterTransferDto;
import hu.sajat.springgyakorlas.dto.SummaryDataDto;
import hu.sajat.springgyakorlas.dto.SummaryDto;
import hu.sajat.springgyakorlas.dto.TransferDto;
import hu.sajat.springgyakorlas.services.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransferService transferService;

    @GetMapping
    public List<TransferDto> getAllTransfer() {
        log.info("HTTP GET METHOD - getAllTransfer");
        return transferService.findAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTransfer(@RequestBody RegisterTransferDto registerTransferDto){
        transferService.saveTransfer(registerTransferDto);
        log.info("HTTP Post METHOD - saveTransfer");
    }
    @GetMapping("/summary")
    public SummaryDataDto summary(@RequestBody SummaryDto summaryDto){
        return transferService.summary(summaryDto);
    }
}
