package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class StateCorrelationHisIndividualService {

    @Inject
    private StateCorrelationHisIndividualRepository stateCorrelationHisIndividualRepository;

    @Inject
    private StateLinkSettingMasterRepository stateLinkSettingMasterRepository;


    public void addHistoryIndividual(String newHistID, YearMonth start, YearMonth end, List<StateLinkSettingMaster> stateLinkSettingMaster, StateLinkSettingDate baseDate){
        String cId = AppContexts.user().companyId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        StateCorrelationHisIndividual hisIndividual = new StateCorrelationHisIndividual(cId, new ArrayList<YearMonthHistoryItem>());
        Optional<StateCorrelationHisIndividual> itemtoBeAdded = stateCorrelationHisIndividualRepository.getStateCorrelationHisIndividualById(cId);
        if(itemtoBeAdded.isPresent()) {
            hisIndividual = itemtoBeAdded.get();
        }
        hisIndividual.add(yearMonthItem);
        stateCorrelationHisIndividualRepository.add(cId, yearMonthItem);
        this.updateItemBefore(itemtoBeAdded.get(), yearMonthItem, cId);
        stateLinkSettingMasterRepository.addAll(stateLinkSettingMaster);
    }

    public void updateHistoryIndividual(List<StateLinkSettingMaster> stateLinkSettingMaster){
        stateLinkSettingMasterRepository.updateAll(stateLinkSettingMaster);
    }

    private void updateItemBefore(StateCorrelationHisIndividual stateCorrelationHisIndividual, YearMonthHistoryItem item, String cId){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisIndividual.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorrelationHisIndividualRepository.update(cId, itemToBeUpdated.get());
    }
    
}
