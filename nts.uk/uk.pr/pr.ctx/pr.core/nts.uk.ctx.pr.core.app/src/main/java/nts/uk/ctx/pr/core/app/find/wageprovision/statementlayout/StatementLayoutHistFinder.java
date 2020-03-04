package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto.YearMonthHistoryItemDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.service.StatementLayoutService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
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
    private StatementLayoutService statementLayoutService;

    private static final int END_MONTH = 999912;

    public List<YearMonthHistoryItemDto> getHistByCidAndCodeAndAfterDate(String code, int startYearMonth) {
        String cid = AppContexts.user().companyId();

        return statementLayoutHistRepo.getHistByCidAndCodeAndAfterDate(cid, code, startYearMonth).stream().
                map(i -> YearMonthHistoryItemDto.fromDomainToDto(i)).collect(Collectors.toList());
    }

    public StatementLayoutAndHistDto getStatementLayoutAndHistById(String code, String histId) {
        String cid = AppContexts.user().companyId();

        Optional<StatementLayout> statementLayout = statementLayoutRepo.getStatementLayoutById(cid, code);
        Optional<YearMonthHistoryItem> yearMonthHistoryItem = statementLayoutHistRepo.getStatementLayoutHistById(cid, code, histId);
        List<YearMonthHistoryItem> yearMonthHistoryItemList = new ArrayList<>();
        yearMonthHistoryItem.ifPresent(i -> yearMonthHistoryItemList.add(i));

        return statementLayout.map(x -> StatementLayoutAndHistDto.fromDomain(x, yearMonthHistoryItemList)).orElse(null);
    }

    public Optional<StatementLayoutHistDataDto> getStatementLayoutHistData(String code, String histId) {
        String cid = AppContexts.user().companyId();

        Optional<StatementLayout> statementLayoutOptional = statementLayoutRepo.getStatementLayoutById(cid, code);
        Optional<YearMonthHistoryItem> yearMonthHistoryItemOptional = statementLayoutHistRepo.getStatementLayoutHistById(cid, code, histId);
        Optional<StatementLayoutSet> statementLayoutSetOptional = statementLayoutSetRepo.getStatementLayoutSetById(cid, code, histId);

        if (statementLayoutOptional.isPresent() && yearMonthHistoryItemOptional.isPresent() && statementLayoutSetOptional.isPresent()) {
            StatementLayout statementLayout = statementLayoutOptional.get();
            YearMonthHistoryItem yearMonthHistoryItem = yearMonthHistoryItemOptional.get();
            StatementLayoutSet statementLayoutSet = statementLayoutSetOptional.get();

            return Optional.of(new StatementLayoutHistDataDto(statementLayout.getStatementCode().v(), statementLayout.getStatementName().v(),
                    yearMonthHistoryItem.identifier(), yearMonthHistoryItem.start().v(), yearMonthHistoryItem.end().v(), new StatementLayoutSetDto(statementLayoutSet)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<StatementLayoutHistDataDto> getInitStatementLayoutHistData(String statementCode, String histId, int startMonth, int itemHistoryDivision, int layoutPattern) {
        String cid = AppContexts.user().companyId();

        Optional<StatementLayout> statementLayoutOptional = statementLayoutRepo.getStatementLayoutById(cid, statementCode);
        Optional<StatementLayoutSet> statementLayoutSetOptional = statementLayoutService.initStatementLayoutData(statementCode, histId, itemHistoryDivision, layoutPattern);

        if(statementLayoutOptional.isPresent() && statementLayoutSetOptional.isPresent()) {
            StatementLayout statementLayout = statementLayoutOptional.get();
            StatementLayoutSet statementLayoutSet = statementLayoutSetOptional.get();

            return Optional.of(new StatementLayoutHistDataDto(statementLayout.getStatementCode().v(), statementLayout.getStatementName().v(),
                    histId, startMonth, END_MONTH, new StatementLayoutSetDto(statementLayoutSet)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<StatementLayoutHistDataDto> getLastStatementLayoutHistData(String code) {
        String cid = AppContexts.user().companyId();

        Optional<StatementLayout> statementLayoutOptional = statementLayoutRepo.getStatementLayoutById(cid, code);
        StatementLayoutHist statementLayoutHist = statementLayoutHistRepo.getLayoutHistByCidAndCode(cid, code);
        Optional<YearMonthHistoryItem> yearMonthHistoryItemLastOptional = statementLayoutHist.latestStartItem();

        if (statementLayoutOptional.isPresent() && yearMonthHistoryItemLastOptional.isPresent()) {
            StatementLayout statementLayout = statementLayoutOptional.get();
            YearMonthHistoryItem yearMonthHistoryItemLast = yearMonthHistoryItemLastOptional.get();
            String histId = yearMonthHistoryItemLast.identifier();
            StatementLayoutSetDto statementLayoutSetDto = statementLayoutSetRepo.getStatementLayoutSetById(cid, code, histId).map(i -> new StatementLayoutSetDto(i)).orElse(null);

            StatementLayoutHistDataDto result = new StatementLayoutHistDataDto(statementLayout.getStatementCode().v(), statementLayout.getStatementName().v(),
                    yearMonthHistoryItemLast.identifier(), yearMonthHistoryItemLast.start().v(), yearMonthHistoryItemLast.end().v(),
                    statementLayoutSetDto);

            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    public List<StatementNameLayoutHistDto> getAllStatementLayoutHist(int startYearMonth) {
        String cid = AppContexts.user().companyId();
        List<StatementNameLayoutHistDto> resulf = new ArrayList<StatementNameLayoutHistDto>();
        List<StatementLayoutHist> listStatementLayoutHistory = statementLayoutHistRepo.getAllStatementLayoutHistByCid(cid, startYearMonth);
        List<StatementLayout> listStatementLayout = statementLayoutRepo.getStatementLayoutByCId(cid);
        listStatementLayoutHistory.forEach(item -> {
            resulf.add(new StatementNameLayoutHistDto(item.getCid(), item.getStatementCode().v(),
                    listStatementLayout.stream().filter(elementToSearch -> elementToSearch.getStatementCode().v().equals(item.getStatementCode().v())).findFirst().get().getStatementName().v(),
                    item.getHistory().get(0).identifier(),
                    item.getHistory().get(0).start().v(),
                    item.getHistory().get(0).end().v()
            ));
        });
        return resulf;
    }
}
