package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;


import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class StateCorreHisComService {

    @Inject
    StateCorreHisComRepository stateCorreHisComRepository;

    public void addStateCorrelationHisCompany(String cid, String hisID,YearMonth start, YearMonth end, String salaryCode, String bonusCode){
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(hisID, new YearMonthPeriod(start, end));
        StateCorreHisCom stateCorreHisCom = new StateCorreHisCom(cid,new ArrayList<>());
        Optional<StateCorreHisCom> oStateCorrelationHisCompany = stateCorreHisComRepository.getStateCorrelationHisCompanyById(cid);
        if(oStateCorrelationHisCompany.isPresent()){
            stateCorreHisCom = oStateCorrelationHisCompany.get();
        }
        stateCorreHisCom.add(yearMonthItem);
        stateCorreHisComRepository.add(cid,yearMonthItem,salaryCode,bonusCode);
        this.updateItemBefore(cid,yearMonthItem, stateCorreHisCom,salaryCode,bonusCode);
    }

    public void updateStateCorrelationHisCompany(String cid, String hisID,YearMonth start, YearMonth end, String salaryCode, String bonusCode){
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(hisID, new YearMonthPeriod(start, end));
        stateCorreHisComRepository.update(cid,yearMonthItem,salaryCode,bonusCode);
    }


    private void updateItemBefore(String cId, YearMonthHistoryItem item, StateCorreHisCom stateCorreHisCom, String salaryCode, String bonusCode){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHisCom.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorreHisComRepository.update(cId, itemToBeUpdated.get(),salaryCode,bonusCode);
    }
}
