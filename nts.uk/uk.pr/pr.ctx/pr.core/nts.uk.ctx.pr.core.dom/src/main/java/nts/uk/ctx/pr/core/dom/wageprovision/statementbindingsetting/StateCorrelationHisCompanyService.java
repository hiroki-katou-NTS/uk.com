package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;


import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class StateCorrelationHisCompanyService {

    @Inject
    StateCorrelationHisCompanyRepository stateCorrelationHisCompanyRepository;

    public void addStateCorrelationHisCompany(String cid, String hisID,YearMonth start, YearMonth end, String salaryCode, String bonusCode){
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(hisID, new YearMonthPeriod(start, end));
        StateCorrelationHisCompany stateCorrelationHisCompany = new StateCorrelationHisCompany(cid,new ArrayList<>());
        Optional<StateCorrelationHisCompany> oStateCorrelationHisCompany = stateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyById(cid);
        if(oStateCorrelationHisCompany.isPresent()){
            stateCorrelationHisCompany = oStateCorrelationHisCompany.get();
        }
        stateCorrelationHisCompany.add(yearMonthItem);
        stateCorrelationHisCompanyRepository.add(cid,yearMonthItem,salaryCode,bonusCode);
        this.updateItemBefore(cid,yearMonthItem,stateCorrelationHisCompany,salaryCode,bonusCode);
    }

    public void updateStateCorrelationHisCompany(String cid, String hisID,YearMonth start, YearMonth end, String salaryCode, String bonusCode){
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(hisID, new YearMonthPeriod(start, end));
        stateCorrelationHisCompanyRepository.update(cid,yearMonthItem,salaryCode,bonusCode);
    }


    private void updateItemBefore( String cId, YearMonthHistoryItem item, StateCorrelationHisCompany stateCorrelationHisCompany, String salaryCode, String bonusCode){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisCompany.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorrelationHisCompanyRepository.update(cId, itemToBeUpdated.get(),salaryCode,bonusCode);
    }
}
