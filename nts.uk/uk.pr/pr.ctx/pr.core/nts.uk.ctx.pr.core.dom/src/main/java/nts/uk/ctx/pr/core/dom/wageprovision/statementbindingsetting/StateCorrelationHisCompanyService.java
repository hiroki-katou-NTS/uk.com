package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;


import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class StateCorrelationHisCompanyService {

    @Inject
    StateCorrelationHisCompanyRepository stateCorrelationHisCompanyRepository;

    public void addStateCorrelationHisCompany(String cid, String hisID,YearMonth start, YearMonth end){
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(hisID, new YearMonthPeriod(start, end));
        StateCorrelationHisCompany stateCorrelationHisCompany = new StateCorrelationHisCompany(cid,new ArrayList<>());
        Optional<StateCorrelationHisCompany> oStateCorrelationHisCompany = stateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyById(cid);
        if(oStateCorrelationHisCompany.isPresent()){
            stateCorrelationHisCompany = oStateCorrelationHisCompany.get();
        }
        stateCorrelationHisCompany.add(yearMonthItem);
        stateCorrelationHisCompanyRepository.add(cid,yearMonthItem);
        this.updateItemBefore(cid,yearMonthItem,stateCorrelationHisCompany);
    }

    private void updateItemBefore( String cId, YearMonthHistoryItem item, StateCorrelationHisCompany stateCorrelationHisCompany){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisCompany.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorrelationHisCompanyRepository.update(cId, itemToBeUpdated.get());
    }
}
