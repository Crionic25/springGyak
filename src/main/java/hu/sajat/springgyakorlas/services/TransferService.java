package hu.sajat.springgyakorlas.services;

import hu.sajat.springgyakorlas.dto.RegisterTransferDto;
import hu.sajat.springgyakorlas.dto.SummaryDataDto;
import hu.sajat.springgyakorlas.dto.SummaryDto;
import hu.sajat.springgyakorlas.dto.TransferDto;

import java.util.List;

public interface TransferService {
    List<TransferDto> findAll();

    void saveTransfer(RegisterTransferDto transferDto);

    SummaryDataDto summary(SummaryDto summaryDto);
}
