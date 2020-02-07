package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class StateCorreHisIndiviService {

    @Inject
    private StateCorreHisIndiviRepository stateCorreHisIndiviRepository;

    public void addHistoryIndividual(String newHistID, YearMonth start, YearMonth end, StateLinkSetIndivi stateLinkSetIndivi, String empId){
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
        StateCorreHisIndivi hisIndividual = new StateCorreHisIndivi(empId, new ArrayList<YearMonthHistoryItem>());
        Optional<StateCorreHisIndivi> itemtoBeAdded = stateCorreHisIndiviRepository.getStateCorrelationHisIndividualByEmpId(empId);
        if(itemtoBeAdded.isPresent()) {
            hisIndividual = itemtoBeAdded.get();
        }
        hisIndividual.add(yearMonthItem);
        stateCorreHisIndiviRepository.add(empId, yearMonthItem,
                stateLinkSetIndivi.getSalaryCode().isPresent() ? stateLinkSetIndivi.getSalaryCode().get().v() : null,
                stateLinkSetIndivi.getBonusCode().isPresent() ? stateLinkSetIndivi.getBonusCode().get().v() : null);
    }

    public void updateHistoryIndividual(YearMonthHistoryItem history, StateLinkSetIndivi stateLinkSetIndivi, String empId){
        stateCorreHisIndiviRepository.update(empId,history,
                stateLinkSetIndivi.getSalaryCode().isPresent() ? stateLinkSetIndivi.getSalaryCode().get().v() : null,
                stateLinkSetIndivi.getBonusCode().isPresent() ? stateLinkSetIndivi.getBonusCode().get().v() : null);
    }

    
}
