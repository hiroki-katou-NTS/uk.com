package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto.YearMonthHistoryItemDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class StatementLayoutHistFinder {
    @Inject
    private StatementLayoutRepository statementLayoutRepo;
    @Inject
    private StatementLayoutHistRepository statementLayoutHistRepo;
    @Inject
    private StatementLayoutSetRepository statementLayoutSetRepo;
    @Inject
    private PaymentItemDetailSetRepository paymentItemDetailSetRepo;
    @Inject
    private DeductionItemDetailSetRepository deductionItemDetailSetRepo;

    public List<YearMonthHistoryItemDto> getHistByCidAndCodeAndAfterDate(String code, int startYearMonth) {
        String cid = AppContexts.user().companyId();

        return statementLayoutHistRepo.getHistByCidAndCodeAndAfterDate(cid, code, startYearMonth).stream().
                map(i -> YearMonthHistoryItemDto.fromDomainToDto(i)).collect(Collectors.toList());
    }

    public Optional<StatementLayoutHistDataDto> getStatementLayoutHistData(String code, String histId) {
        String cid = AppContexts.user().companyId();

        Optional<StatementLayout> statementLayoutOptional = statementLayoutRepo.getStatementLayoutById(cid, code);
        Optional<YearMonthHistoryItem> yearMonthHistoryItemOptional = statementLayoutHistRepo.getStatementLayoutHistById(histId);
        Optional<StatementLayoutSet> statementLayoutSetOptional = statementLayoutSetRepo.getStatementLayoutSetById(histId);

        if(statementLayoutOptional.isPresent() && yearMonthHistoryItemOptional.isPresent() && statementLayoutSetOptional.isPresent()) {
            StatementLayout statementLayout = statementLayoutOptional.get();
            YearMonthHistoryItem yearMonthHistoryItem = yearMonthHistoryItemOptional.get();
            StatementLayoutSet statementLayoutSet = statementLayoutSetOptional.get();

            return Optional.of(new StatementLayoutHistDataDto(statementLayout.getStatementCode().v(), statementLayout.getStatementName().v(),
                    yearMonthHistoryItem.identifier(), yearMonthHistoryItem.start().v(), yearMonthHistoryItem.end().v(), new StatementLayoutSetDto(statementLayoutSet)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<StatementLayoutHistDataDto> getLastStatementLayoutHistData(String code) {
        String cid = AppContexts.user().companyId();

        Optional<StatementLayout> statementLayoutOptional = statementLayoutRepo.getStatementLayoutById(cid, code);
        StatementLayoutHist statementLayoutHist = statementLayoutHistRepo.getLayoutHistByCidAndCode(cid, code);
        Optional<YearMonthHistoryItem> yearMonthHistoryItemLastOptional = statementLayoutHist.latestStartItem();

        if(statementLayoutOptional.isPresent() && yearMonthHistoryItemLastOptional.isPresent()) {
            StatementLayout statementLayout = statementLayoutOptional.get();
            YearMonthHistoryItem yearMonthHistoryItemLast = yearMonthHistoryItemLastOptional.get();
            String histId = yearMonthHistoryItemLast.identifier();
            StatementLayoutSetDto statementLayoutSetDto = statementLayoutSetRepo.getStatementLayoutSetById(histId).map(i -> new StatementLayoutSetDto(i)).orElse(null);

            StatementLayoutHistDataDto result = new StatementLayoutHistDataDto(statementLayout.getStatementCode().v(), statementLayout.getStatementName().v(),
                    yearMonthHistoryItemLast.identifier(), yearMonthHistoryItemLast.start().v(), yearMonthHistoryItemLast.end().v(),
                    statementLayoutSetDto);

            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }
}
