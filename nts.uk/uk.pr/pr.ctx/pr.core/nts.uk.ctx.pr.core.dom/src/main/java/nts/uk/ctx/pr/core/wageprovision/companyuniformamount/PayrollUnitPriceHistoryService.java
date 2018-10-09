package nts.uk.ctx.pr.core.wageprovision.companyuniformamount;

import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class PayrollUnitPriceHistoryService {

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

    public void updatePayrollUnitPriceHistory(String hisId,String code, YearMonth startYearMonth,YearMonth endYearMonth ){
        String cId = AppContexts.user().companyId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(hisId, new YearMonthPeriod(startYearMonth, endYearMonth));

        PayrollUnitPriceHistory itemtoBeAdded = new PayrollUnitPriceHistory(new CompanyUnitPriceCode(code),cId, new ArrayList<>());
        Optional<PayrollUnitPriceHistory> mPayrollUnitPriceHistory =  mPayrollUnitPriceHistoryRepository.getPayrollUnitPriceHistoryByCidCode(cId,code);

        if (mPayrollUnitPriceHistory.isPresent()) {
            itemtoBeAdded = mPayrollUnitPriceHistory.get();
        }
        itemtoBeAdded.add(yearMonthItem);
        this.addPayrollUnitPriceHis(yearMonthItem,cId,code);
        this.updateItemBefore(mPayrollUnitPriceHistory.get(), yearMonthItem, cId,code);
    }
    public void deletePayrollUnitPriceHis(String hisId,String code ){
        String cId = AppContexts.user().companyId();
        this.mPayrollUnitPriceHistoryRepository.remove(cId,code,hisId);
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





}
