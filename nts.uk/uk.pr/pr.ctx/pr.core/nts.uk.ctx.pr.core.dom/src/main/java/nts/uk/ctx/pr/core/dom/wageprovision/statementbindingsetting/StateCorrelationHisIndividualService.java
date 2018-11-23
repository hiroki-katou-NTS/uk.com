package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class StateCorrelationHisIndividualService {

    @Inject
    private StateCorrelationHisIndividualRepository stateCorrelationHisIndividualRepository;

    public void addHistoryIndividual(String newHistID, YearMonth start, YearMonth end, StateLinkSettingIndividual stateLinkSettingIndividual, String empId){
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        StateCorrelationHisIndividual hisIndividual = new StateCorrelationHisIndividual(empId, new ArrayList<YearMonthHistoryItem>());
        Optional<StateCorrelationHisIndividual> itemtoBeAdded = stateCorrelationHisIndividualRepository.getStateCorrelationHisIndividualByEmpId(empId);
        if(itemtoBeAdded.isPresent()) {
            hisIndividual = itemtoBeAdded.get();
        }
        hisIndividual.add(yearMonthItem);
        stateCorrelationHisIndividualRepository.add(empId, yearMonthItem,stateLinkSettingIndividual.getSalaryCode().get().v(),stateLinkSettingIndividual.getBonusCode().get().v());
    }

    public void updateHistoryIndividual(YearMonthHistoryItem history,StateLinkSettingIndividual stateLinkSettingIndividual, String empId){
        stateCorrelationHisIndividualRepository.update(empId,history,stateLinkSettingIndividual.getSalaryCode().get().v(),stateLinkSettingIndividual.getBonusCode().get().v());
    }

    
}
