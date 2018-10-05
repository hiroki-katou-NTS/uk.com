package nts.uk.ctx.pr.core.dom.laborinsurance;

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
public class OccAccInsurBusiBurdenRatioService {
    @Inject
    private OccAccIsHisRepository occAccIsHisRepository;

    @Inject
    private OccAccIsPrRateRepository occAccIsPrRateRepository;


    public void addOccAccInsurBusiBurdenRatio(List<OccAccInsurBusiBurdenRatio> occAccInsurBusiBurdenRatioList, YearMonth start, YearMonth end){
        String cId = AppContexts.user().companyId();
        String newHistID = IdentifierUtil.randomUniqueId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        OccAccIsHis itemtoBeAdded = new OccAccIsHis(cId, new ArrayList<>());
        Optional<OccAccIsHis> occAccIsHis = occAccIsHisRepository.getAllOccAccIsHisByCid(cId);
        if (occAccIsHis.isPresent()) {
            itemtoBeAdded = occAccIsHis.get();
        }
        itemtoBeAdded.add(yearMonthItem);
        this.addOccAccIsHis(yearMonthItem,cId);
        this.updateItemBefore(occAccIsHis.get(), yearMonthItem, cId);
        this.addOccAccInsurBusiBurdenRatio(occAccInsurBusiBurdenRatioList,newHistID);

    }
    
    public void updateOccAccInsurBusiBurdenRatio(List<OccAccInsurBusiBurdenRatio> occAccInsurBusiBurdenRatioList,String hisId){
        occAccIsPrRateRepository.update(occAccInsurBusiBurdenRatioList,hisId);
    }
    
    private void addOccAccIsHis(YearMonthHistoryItem itemtoBeAdded, String cId){
        if(itemtoBeAdded == null){
            return;
        }
        occAccIsHisRepository.add(itemtoBeAdded, cId);
    }
    
    private void updateItemBefore(OccAccIsHis occAccIsHis, YearMonthHistoryItem item, String cId){
        Optional<YearMonthHistoryItem> itemToBeUpdated = occAccIsHis.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        occAccIsHisRepository.update(itemToBeUpdated.get(),cId);
    }
    
    private void addOccAccInsurBusiBurdenRatio(List<OccAccInsurBusiBurdenRatio> domain,String hisId){
        occAccIsPrRateRepository.add(domain,hisId);
    }
}
