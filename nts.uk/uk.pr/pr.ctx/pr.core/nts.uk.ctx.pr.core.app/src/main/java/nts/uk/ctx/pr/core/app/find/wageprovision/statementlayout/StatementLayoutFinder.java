package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
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
    private CurrProcessDateRepository currProcessDateRepo;

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

    /**
     * Screen P
     */
    public Integer getCurrentProcessingDate() {
        String cid = AppContexts.user().companyId();
        Optional<CurrProcessDate> processDateOtp = currProcessDateRepo.getCurrProcessDateByIdAndProcessCateNo(cid, 1);
        if (processDateOtp.isPresent()) {
            return processDateOtp.get().getGiveCurrTreatYear().v();
        }
        return null;
    }

    /**
     * Screen P
     */
    public List<StatementLayoutDto> getStatementLayoutByProcessingDate(int processingDate) {
        String cid = AppContexts.user().companyId();
        // ドメインモデル「明細書レイアウト履歴」を取得する
        List<String> sttCodes = statementLayoutHistRepo.getAllStatementLayoutHistByCid(cid, processingDate)
                .stream().map(x -> x.getStatementCode().v()).collect(Collectors.toList());
        // ドメインモデル「明細書レイアウト」を取得する
        List<StatementLayoutDto> sttLayouts = new ArrayList<>();
        if(sttCodes.size() > 0){
            sttLayouts = statementLayoutRepo.getAllStatementLayoutByCidAndCodes(cid, sttCodes)
                    .stream().map(x -> new StatementLayoutDto(x.getStatementCode().v(), x.getStatementName().v()))
                    .collect(Collectors.toList());
        }

        return sttLayouts;
    }

}
