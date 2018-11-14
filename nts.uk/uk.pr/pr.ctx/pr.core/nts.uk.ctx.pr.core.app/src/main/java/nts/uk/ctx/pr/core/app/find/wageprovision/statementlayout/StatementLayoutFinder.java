package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
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
public class StatementLayoutFinder {
    @Inject
    private StatementLayoutRepository statementLayoutRepo;
    @Inject
    private StatementLayoutHistRepository statementLayoutHistRepo;
    @Inject
    private StatementLayoutService statementLayoutService;
    @Inject
    private PaymentItemSetRepository paymentItemSetRepo;
    @Inject
    private DeductionItemSetRepository deductionItemSetRepo;

    @Inject
    private TimeItemSetRepository timeItemSetRepository;

    public StatementLayoutAndHistDto getStatementLayoutAndLastHist(String code) {
        String cid = AppContexts.user().companyId();

        Optional<StatementLayout> statementLayout = statementLayoutRepo.getStatementLayoutById(cid, code);
        Optional<YearMonthHistoryItem> yearMonthHistoryItem = statementLayoutHistRepo.getLatestHistByCidAndCode(cid, code);
        List<YearMonthHistoryItem> yearMonthHistoryItemList = new ArrayList<>();
        yearMonthHistoryItem.ifPresent(i -> yearMonthHistoryItemList.add(i));

        return statementLayout.map(x -> StatementLayoutAndHistDto.fromDomain(x, yearMonthHistoryItemList)).orElse(null);
    }

    public List<StatementLayoutAndHistDto> getAllStatementLayoutAndHist() {
        List<StatementLayoutAndHistDto> result = new ArrayList<>();
        String cid = AppContexts.user().companyId();

        List<StatementLayout> statementLayoutList = statementLayoutRepo.getAllStatementLayoutByCid(cid);

        for(StatementLayout statementLayout : statementLayoutList) {
            List<YearMonthHistoryItem> yearMonthHistoryItemList = statementLayoutHistRepo.getAllHistByCidAndCode(cid, statementLayout.getStatementCode().v());
            result.add(StatementLayoutAndHistDto.fromDomain(statementLayout, yearMonthHistoryItemList));
        }

        return result;
    }

    public List<StatementLayoutAndHistDto> getAllStatementLayoutAndLastHist() {
        List<StatementLayoutAndHistDto> result = new ArrayList<>();
        String cid = AppContexts.user().companyId();

        List<StatementLayout> statementLayoutList = statementLayoutRepo.getAllStatementLayoutByCid(cid);

        for(StatementLayout statementLayout : statementLayoutList) {
            Optional<YearMonthHistoryItem> yearMonthHistoryItemOptional = statementLayoutHistRepo.getLatestHistByCidAndCode(cid, statementLayout.getStatementCode().v());

            if(yearMonthHistoryItemOptional.isPresent()) {
                List<YearMonthHistoryItem> yearMonthHistoryItemList = new ArrayList<>();
                yearMonthHistoryItemList.add(yearMonthHistoryItemOptional.get());
                result.add(StatementLayoutAndHistDto.fromDomain(statementLayout,yearMonthHistoryItemList));
            }
        }

        return result;
    }

    public List<StatementItemCustomDto> getStatementItem(StatementItemCustomDataDto dataDto) {
        return statementLayoutService.getStatementItem(dataDto.getCategoryAtr(), dataDto.getItemNameCdSelected(), dataDto.getItemNameCdExcludeList())
                .stream().map(StatementItemCustomDto::new).collect(Collectors.toList());
    }

    public PaymentItemSetDto getPaymentItemStById(int categoryAtr, String itemNameCode) {
        String cid = AppContexts.user().companyId();
        Optional<PaymentItemSet> paymentItemSetOpt = paymentItemSetRepo.getPaymentItemStById(cid, categoryAtr, itemNameCode);
        return paymentItemSetOpt.map(PaymentItemSetDto::new).orElse(null);
    }

    public DeductionItemSetDto getDeductionItemStById(int categoryAtr, String itemNameCode) {
        String cid = AppContexts.user().companyId();
        Optional<DeductionItemSet> deductionItemSetOpt = deductionItemSetRepo.getDeductionItemStById(cid, categoryAtr, itemNameCode);
        return deductionItemSetOpt.map(DeductionItemSetDto::new).orElse(null);
    }

    public AttendanceItemSetDto getAttendanceItemStById(int categoryAtr, String itemNameCode) {
        String cid = AppContexts.user().companyId();
        Optional<TimeItemSet> attItem = timeItemSetRepository.getTimeItemStById(cid, categoryAtr, itemNameCode);
        return attItem.map(AttendanceItemSetDto::new).orElse(null);
    }

}
