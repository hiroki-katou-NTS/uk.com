package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class StateCorrelationHisPositionService {

    @Inject
    private StateCorrelationHisPositionRepository stateCorrelationHisPositionRepository;

    @Inject
    private StateLinkSettingMasterRepository stateLinkSettingMasterRepository;

    @Inject
    private StateLinkSettingDateRepository stateLinkSettingDateRepository;

    public void addHistoryPosition(String newHistID, YearMonth start, YearMonth end, List<StateLinkSettingMaster> stateLinkSettingMaster, StateLinkSettingDate baseDate){
        String cId = AppContexts.user().companyId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        Optional<StateCorrelationHisPosition> itemtoBeAdded = stateCorrelationHisPositionRepository.getStateCorrelationHisPositionByCid(cId);
        itemtoBeAdded.get().add(yearMonthItem);
        stateCorrelationHisPositionRepository.add(cId, yearMonthItem);
        this.updateItemBefore(itemtoBeAdded.get(), yearMonthItem, cId);
        stateLinkSettingDateRepository.add(baseDate);
        stateLinkSettingMasterRepository.addAll(stateLinkSettingMaster);
    }

    public void updateHistoryPosition(List<StateLinkSettingMaster> stateLinkSettingMaster){
        stateLinkSettingMasterRepository.updateAll(stateLinkSettingMaster);
    }

    private void updateItemBefore(StateCorrelationHisPosition stateCorrelationHisPosition, YearMonthHistoryItem item, String cId){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisPosition.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorrelationHisPositionRepository.update(cId, itemToBeUpdated.get());
    }
    
}
