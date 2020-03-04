package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class StateCorreHisClsService {

    @Inject
    private StateCorreHisClsRepository stateCorreHisClsRepository;


    public void addHistoryClassification(String newHistID, YearMonth start, YearMonth end, List<StateLinkSetMaster> stateLinkSetMaster){
        String cId = AppContexts.user().companyId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        StateCorreHisCls hisClassification = new StateCorreHisCls(cId, new ArrayList<YearMonthHistoryItem>());
        Optional<StateCorreHisCls> itemtoBeAdded = stateCorreHisClsRepository.getStateCorrelationHisClassificationByCid(cId);
        if(itemtoBeAdded.isPresent()){
            hisClassification = itemtoBeAdded.get();
        }
        hisClassification.add(yearMonthItem);
        stateCorreHisClsRepository.addAll(cId, stateLinkSetMaster, yearMonthItem.start().v(), yearMonthItem.end().v());
        this.updateItemBefore(hisClassification, yearMonthItem, cId);
    }

    public void updateHistoryClassification(String hisId, List<StateLinkSetMaster> stateLinkSetMaster, YearMonth start, YearMonth end){
        String cId = AppContexts.user().companyId();
        stateCorreHisClsRepository.removeAll(cId,hisId);
        stateCorreHisClsRepository.addAll(cId, stateLinkSetMaster,start.v(),end.v());
    }

    private void updateItemBefore(StateCorreHisCls stateCorreHisCls, YearMonthHistoryItem item, String cId){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHisCls.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorreHisClsRepository.update(cId, itemToBeUpdated.get());
    }
    
}
