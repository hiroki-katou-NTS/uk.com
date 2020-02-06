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
public class StateCorreHisPoService {

    @Inject
    private StateCorreHisPoRepository stateCorreHisPoRepository;


    public void addHistoryPosition(String newHistID, YearMonth start, YearMonth end, List<StateLinkSetMaster> stateLinkSetMaster, StateLinkSetDate baseDate){
        String cId = AppContexts.user().companyId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        StateCorreHisPo hisPosition = new StateCorreHisPo(cId, new ArrayList<YearMonthHistoryItem>());
        Optional<StateCorreHisPo> itemtoBeAdded = stateCorreHisPoRepository.getStateCorrelationHisPositionByCid(cId);
        if(itemtoBeAdded.isPresent()) {
            hisPosition = itemtoBeAdded.get();
        }
        hisPosition.add(yearMonthItem);
        this.updateItemBefore(hisPosition, yearMonthItem, cId);
        stateCorreHisPoRepository.addAll(cId, stateLinkSetMaster,start.v(), end.v(),baseDate.getDate());
    }

    public void updateHistoryPosition(String hisId, List<StateLinkSetMaster> stateLinkSetMaster, YearMonth start, YearMonth end, StateLinkSetDate baseDate){
        String cId = AppContexts.user().companyId();
        stateCorreHisPoRepository.removeAll(cId,hisId);
        stateCorreHisPoRepository.addAll(cId, stateLinkSetMaster,start.v(), end.v(),baseDate.getDate());
    }

    private void updateItemBefore(StateCorreHisPo stateCorreHisPo, YearMonthHistoryItem item, String cId){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHisPo.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorreHisPoRepository.update(cId, itemToBeUpdated.get());
    }
    
}
