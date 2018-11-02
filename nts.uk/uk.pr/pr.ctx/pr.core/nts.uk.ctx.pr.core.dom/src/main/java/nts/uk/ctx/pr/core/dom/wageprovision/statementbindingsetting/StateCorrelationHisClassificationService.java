package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class StateCorrelationHisClassificationService {

    @Inject
    private StateCorrelationHisClassificationRepository stateCorrelationHisClassificationRepository;

    @Inject
    private StateLinkSettingMasterRepository stateLinkSettingMasterRepository;

    public void addHistoryClassification(String newHistID, YearMonth start, YearMonth end, List<StateLinkSettingMaster> stateLinkSettingMaster){
        String cId = AppContexts.user().companyId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        Optional<StateCorrelationHisClassification> itemtoBeAdded = stateCorrelationHisClassificationRepository.getStateCorrelationHisClassificationByCid(cId);
        itemtoBeAdded.get().add(yearMonthItem);
        stateCorrelationHisClassificationRepository.add(cId, yearMonthItem);
        this.updateItemBefore(itemtoBeAdded.get(), yearMonthItem, cId);
        stateLinkSettingMasterRepository.addAll(stateLinkSettingMaster);
    }

    public void updateHistoryClassification(List<StateLinkSettingMaster> stateLinkSettingMaster){
        stateLinkSettingMasterRepository.updateAll(stateLinkSettingMaster);
    }

    private void updateItemBefore(StateCorrelationHisClassification stateCorrelationHisClassification, YearMonthHistoryItem item, String cId){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisClassification.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorrelationHisClassificationRepository.update(cId, itemToBeUpdated.get());
    }
    
}
