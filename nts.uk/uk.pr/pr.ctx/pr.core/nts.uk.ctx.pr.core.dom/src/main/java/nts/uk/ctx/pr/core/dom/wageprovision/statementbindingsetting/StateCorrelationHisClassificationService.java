package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.List;
import java.util.ArrayList;
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


    public void addHistoryClassification(String newHistID, YearMonth start, YearMonth end, List<StateLinkSettingMaster> stateLinkSettingMaster){
        String cId = AppContexts.user().companyId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        StateCorrelationHisClassification hisClassification = new StateCorrelationHisClassification(cId, new ArrayList<YearMonthHistoryItem>());
        Optional<StateCorrelationHisClassification> itemtoBeAdded = stateCorrelationHisClassificationRepository.getStateCorrelationHisClassificationByCid(cId);
        if(itemtoBeAdded.isPresent()){
            hisClassification = itemtoBeAdded.get();
        }
        hisClassification.add(yearMonthItem);
        this.updateItemBefore(hisClassification, yearMonthItem, cId);
    }

    public void updateHistoryClassification(String hisId, List<StateLinkSettingMaster> stateLinkSettingMaster, YearMonth start, YearMonth end){
        String cId = AppContexts.user().companyId();
        stateCorrelationHisClassificationRepository.removeAll(cId,hisId);
        stateCorrelationHisClassificationRepository.addAll(cId,stateLinkSettingMaster,start.v(),end.v());
    }

    private void updateItemBefore(StateCorrelationHisClassification stateCorrelationHisClassification, YearMonthHistoryItem item, String cId){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisClassification.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorrelationHisClassificationRepository.update(cId, itemToBeUpdated.get());
    }
    
}
