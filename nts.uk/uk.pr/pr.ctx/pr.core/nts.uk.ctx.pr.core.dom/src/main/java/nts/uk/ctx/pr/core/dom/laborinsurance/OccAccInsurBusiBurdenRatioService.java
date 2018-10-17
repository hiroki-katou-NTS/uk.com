package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class OccAccInsurBusiBurdenRatioService {
  
    @Inject
    private OccAccIsPrRateRepository occAccIsPrRateRepository;

    public void addOccAccInsurBusiBurdenRatio(List<OccAccInsurBusiBurdenRatio> occAccInsurBusiBurdenRatioList, YearMonth start, YearMonth end){
        String cId = AppContexts.user().companyId();
        String newHistID = IdentifierUtil.randomUniqueId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        OccAccIsHis itemtoBeAdded = occAccIsPrRateRepository.getOccAccIsHisByCid(cId);
        itemtoBeAdded.add(yearMonthItem);
        this.updateItemBefore(itemtoBeAdded, yearMonthItem, cId);
        this.addOccAccInsurBusiBurdenRatio(occAccInsurBusiBurdenRatioList,cId, yearMonthItem);
    }
    
    public void updateOccAccInsurBusiBurdenRatio(List<OccAccInsurBusiBurdenRatio> occAccInsurBusiBurdenRatioList, YearMonthHistoryItem item){
    	String cId = AppContexts.user().companyId();
        occAccIsPrRateRepository.update(occAccInsurBusiBurdenRatioList, cId,item);
    }
    
  
    
    private void updateItemBefore(OccAccIsHis occAccIsHis, YearMonthHistoryItem item, String cId){
        Optional<YearMonthHistoryItem> itemToBeUpdated = occAccIsHis.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        occAccIsPrRateRepository.update(cId, itemToBeUpdated.get());
    }
    
    private void addOccAccInsurBusiBurdenRatio(List<OccAccInsurBusiBurdenRatio> domain, String cId, YearMonthHistoryItem item){
        occAccIsPrRateRepository.add(domain, cId, item);
    }
}
