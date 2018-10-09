package nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class PayrollUnitPriceHistoryService {

    @Inject
    private PayrollUnitPriceHistoryRepository mPayrollUnitPriceHistoryRepository;

    @Inject
    private PayrollUnitPriceSettingRepository mPayrollUnitPriceSettingRepository;

    @Inject
    private PayrollUnitPriceRepository mPayrollUnitPriceRepository;


    public void historyDeletionProcessing(String hisId, String cId,String code){
        Optional<PayrollUnitPriceHistory> accInsurHis = mPayrollUnitPriceHistoryRepository.getPayrollUnitPriceHistoryByCidCode(cId,code);
        if (!accInsurHis.isPresent()) {
            throw new RuntimeException("invalid employmentHistory");
        }
        Optional<YearMonthHistoryItem> itemToBeDelete = accInsurHis.get().getHistory().stream()
                .filter(h -> h.identifier().equals(hisId))
                .findFirst();
        accInsurHis.get().remove(itemToBeDelete.get());
        this.mPayrollUnitPriceSettingRepository.remove(hisId);
        mPayrollUnitPriceHistoryRepository.remove(cId,code, hisId);
        if (accInsurHis.get().getHistory().size() > 0 ){
            YearMonthHistoryItem lastestItem = accInsurHis.get().getHistory().get(0);
            accInsurHis.get().exCorrectToRemove(lastestItem);
            mPayrollUnitPriceHistoryRepository.update(lastestItem, cId,code);
        }
    }
    public void historyCorrectionProcecessing(String cId, String hisId,String code, YearMonth start, YearMonth end){
        Optional<PayrollUnitPriceHistory> accInsurHis = mPayrollUnitPriceHistoryRepository.getPayrollUnitPriceHistoryByCidCode(cId,code);
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


    private void addPayrollUnitPriceHis(YearMonthHistoryItem itemtoBeAdded, String cId,String code){
        if(itemtoBeAdded == null){
            return;
        }
        mPayrollUnitPriceHistoryRepository.add(itemtoBeAdded, cId,code);
    }
    private void updateItemBefore(PayrollUnitPriceHistory payrollUnitPriceHistory, YearMonthHistoryItem item, String cId,String code){
        Optional<YearMonthHistoryItem> itemToBeUpdated = payrollUnitPriceHistory.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        mPayrollUnitPriceHistoryRepository.update(itemToBeUpdated.get(),cId,code);
    }
    private void updatePayrollUnitPriceHis(YearMonthHistoryItem itemToBeUpdated, String cId,String code){
        mPayrollUnitPriceHistoryRepository.update(itemToBeUpdated, cId,code);
    }





}
