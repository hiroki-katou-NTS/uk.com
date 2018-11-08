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
public class StateCorrelationHisSalaryService {

    @Inject
    private StateCorrelationHisSalaryRepository stateCorrelationHisSalaryRepository;

    @Inject
    private StateLinkSettingMasterRepository stateLinkSettingMasterRepository;

    public void addHistorySalary(String newHistID, YearMonth start, YearMonth end, List<StateLinkSettingMaster> stateLinkSettingMaster){
        String cId = AppContexts.user().companyId();
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        Optional<StateCorrelationHisSalary> itemtoBeAdded = stateCorrelationHisSalaryRepository.getStateCorrelationHisSalaryByCid(cId);
        itemtoBeAdded.get().add(yearMonthItem);
        stateCorrelationHisSalaryRepository.add(cId, yearMonthItem);
        this.updateItemBefore(itemtoBeAdded.get(), yearMonthItem, cId);
        stateLinkSettingMasterRepository.addAll(stateLinkSettingMaster);
    }

    public void updateHistorySalary(List<StateLinkSettingMaster> stateLinkSettingMaster){
        stateLinkSettingMasterRepository.updateAll(stateLinkSettingMaster);
    }

    private void updateItemBefore(StateCorrelationHisSalary stateCorrelationHisSalary, YearMonthHistoryItem item, String cId){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisSalary.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorrelationHisSalaryRepository.update(cId, itemToBeUpdated.get());
    }
    
}
