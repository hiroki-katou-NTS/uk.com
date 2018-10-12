package nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount;

import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class PayrollUnitPriceHistoryService {

    
    @Inject
    private PayrollUnitPriceRepository mPayrollUnitPriceRepository;



    @Inject
    private PayrollUnitPriceHistoryRepository payrollUnitPriceHistoryRepository;

    public String addPayrollUnitPriceHistory(String code, YearMonth start, YearMonth end ){
        String cId = AppContexts.user().companyId();
        String newHistID = IdentifierUtil.randomUniqueId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        PayrollUnitPriceHistory itemtoBeAdded = new PayrollUnitPriceHistory(new CompanyUnitPriceCode(code), cId, new ArrayList<>());
        Optional<PayrollUnitPriceHistory> payrollUnitPriceHistory = payrollUnitPriceHistoryRepository.getPayrollUnitPriceHistoryByCidCode(cId, code);
        if (payrollUnitPriceHistory.isPresent()) {
            itemtoBeAdded = payrollUnitPriceHistory.get();
        }
        itemtoBeAdded.add(yearMonthItem);
        return newHistID;
    }

    public void addPayrollUnitPriceHistory(String code, String hisID, YearMonth start, YearMonth end, PayrollUnitPriceSetting payrollUnitPriceSetting ){
        String cId = AppContexts.user().companyId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(hisID, new YearMonthPeriod(start, end));
        PayrollUnitPriceHistory itemtoBeAdded = new PayrollUnitPriceHistory(new CompanyUnitPriceCode(code), cId, new ArrayList<>());
        Optional<PayrollUnitPriceHistory> payrollUnitPriceHistory = payrollUnitPriceHistoryRepository.getPayrollUnitPriceHistoryByCidCode(cId, code);
        if (payrollUnitPriceHistory.isPresent()) {
            itemtoBeAdded = payrollUnitPriceHistory.get();
        }
        itemtoBeAdded.add(yearMonthItem);
        payrollUnitPriceHistoryRepository.add(code, cId, yearMonthItem, payrollUnitPriceSetting);
        this.updateItemBefore(itemtoBeAdded, yearMonthItem, cId, code);
    }

    public void historyDeletionProcessing(String hisId, String cId,String code){

        Optional<PayrollUnitPriceHistory> accInsurHis = payrollUnitPriceHistoryRepository.getPayrollUnitPriceHistoryByCidCode(cId,code);
        if (!accInsurHis.isPresent()) {
            throw new RuntimeException("invalid employmentHistory");
        }
        Optional<YearMonthHistoryItem> itemToBeDelete = accInsurHis.get().getHistory().stream()
                .filter(h -> h.identifier().equals(hisId))
                .findFirst();
        if (!itemToBeDelete.isPresent()) {
            return;
        }
        accInsurHis.get().remove(itemToBeDelete.get());
        payrollUnitPriceHistoryRepository.remove(cId,code, hisId);
        if (accInsurHis.get().getHistory().size() > 0 ){
            YearMonthHistoryItem lastestItem = accInsurHis.get().getHistory().get(0);
            accInsurHis.get().exCorrectToRemove(lastestItem);
            payrollUnitPriceHistoryRepository.update(code, cId, lastestItem);
        }
    }
    public void historyCorrectionProcecessing(String cId, String hisId,String code, YearMonth start, YearMonth end){
        Optional<PayrollUnitPriceHistory> accInsurHis = payrollUnitPriceHistoryRepository.getPayrollUnitPriceHistoryByCidCode(cId,code);
        if (!accInsurHis.isPresent()) {
            return;
        }
        Optional<YearMonthHistoryItem> itemToBeUpdate = accInsurHis.get().getHistory().stream()
                .filter(h -> h.identifier().equals(hisId)).findFirst();
        if (!itemToBeUpdate.isPresent()) {
            return;
        }
        accInsurHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
        this.updatePayrollUnitPriceHis(itemToBeUpdate.get(), cId,code);
        this.updateItemBefore(accInsurHis.get(), itemToBeUpdate.get(), cId,code);
    }

    private void updateItemBefore(PayrollUnitPriceHistory payrollUnitPriceHistory, YearMonthHistoryItem item, String cId,String code){
        Optional<YearMonthHistoryItem> itemToBeUpdated = payrollUnitPriceHistory.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        payrollUnitPriceHistoryRepository.update(code, cId, itemToBeUpdated.get());
    }
    private void updatePayrollUnitPriceHis(YearMonthHistoryItem itemToBeUpdated, String cId,String code){
        payrollUnitPriceHistoryRepository.update(code, cId, itemToBeUpdated);
    }





}
