package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class StateCorreHisSalaService {

    @Inject
    private StateCorreHisSalaRepository stateCorreHisSalaRepository;

    public void addHistorySalary(String newHistID, YearMonth start, YearMonth end, List<StateLinkSetMaster> stateLinkSetMaster){
        String cId = AppContexts.user().companyId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        StateCorreHisSala hisSalary = new StateCorreHisSala(cId, new ArrayList<YearMonthHistoryItem>());
        Optional<StateCorreHisSala> itemtoBeAdded = stateCorreHisSalaRepository.getStateCorrelationHisSalaryByCid(cId);
        if(itemtoBeAdded.isPresent()) {
            hisSalary = itemtoBeAdded.get();
        }
        hisSalary.add(yearMonthItem);
        stateCorreHisSalaRepository.addAll(cId, stateLinkSetMaster,start.v(), end.v());
        this.updateItemBefore(hisSalary, yearMonthItem, cId);
    }

    public void updateHistorySalary(String hisId, YearMonth start, YearMonth end, List<StateLinkSetMaster> stateLinkSetMaster){
        String cId = AppContexts.user().companyId();
        stateCorreHisSalaRepository.removeAll(cId,hisId);
        stateCorreHisSalaRepository.addAll(cId, stateLinkSetMaster,start.v(), end.v());
    }

    private void updateItemBefore(StateCorreHisSala stateCorreHisSala, YearMonthHistoryItem item, String cId){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHisSala.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorreHisSalaRepository.update(cId, itemToBeUpdated.get());
    }
    
}
