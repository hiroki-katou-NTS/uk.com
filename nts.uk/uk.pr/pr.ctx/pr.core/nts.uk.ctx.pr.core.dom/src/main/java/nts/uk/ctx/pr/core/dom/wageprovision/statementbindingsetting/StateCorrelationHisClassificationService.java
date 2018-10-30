package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurPreRate;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class StateCorrelationHisClassificationService {

    @Inject
    private StateCorrelationHisClassificationRepository stateCorrelationHisClassificationRepository;

    public void addHistoryClassification(YearMonth start, YearMonth end, List<StateLinkSettingMaster> stateLinkSettingMaster){
        String cId = AppContexts.user().companyId();
        String newHistID = IdentifierUtil.randomUniqueId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        Optional<StateCorrelationHisClassification> itemtoBeAdded = stateCorrelationHisClassificationRepository.getStateCorrelationHisClassificationByCid(cId);
        itemtoBeAdded.add(yearMonthItem);
        stateCorrelationHisClassificationRepository.add(cId, yearMonthItem);
        this.updateItemBefore(itemtoBeAdded.get(), yearMonthItem, cId);

    }

    public void updateHistoryClassification(String cId, YearMonthHistoryItem item){
        Optional<StateCorrelationHisClassification>  stateCorrelationHisClassification = stateCorrelationHisClassificationRepository.getStateCorrelationHisClassificationByCid(cId);
            Optional<YearMonthHistoryItem> itemToBeUpdate = stateCorrelationHisClassification.get().getHistory().stream()
                    .filter(h -> h.identifier().equals(hisId)).findFirst();
            if (!itemToBeUpdate.isPresent()) {
                return;
            }
            stateCorrelationHisClassification.changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
            stateCorrelationHisClassificationRepository.update(cId, itemToBeUpdate.get());
            this.updateItemBefore(stateCorrelationHisClassification, itemToBeUpdate.get(), cId);
    }

    private void updateItemBefore(StateCorrelationHisClassification stateCorrelationHisClassification, YearMonthHistoryItem item, String cId){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisClassification.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorrelationHisClassificationRepository.update(cId, itemToBeUpdated.get());
    }
    
}
