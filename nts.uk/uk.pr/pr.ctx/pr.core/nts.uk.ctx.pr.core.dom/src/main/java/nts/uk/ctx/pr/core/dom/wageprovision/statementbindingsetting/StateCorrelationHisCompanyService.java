package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;


import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.CompanyUnitPriceCode;
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
        Optional<StateCorrelationHisCompany> stateCorrelationHisCompany = stateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyById(cid,hisID);
        if(stateCorrelationHisCompany.isPresent()){
            stateCorrelationHisCompany.get().add(yearMonthItem);
            stateCorrelationHisCompanyRepository.add(cid,yearMonthItem);
            this.updateItemBefore(cid,yearMonthItem,stateCorrelationHisCompany.get());
        }
    }

    public void historyCorrectionProcecessing(String cid, String hisId, YearMonth start, YearMonth end){
        Optional<StateCorrelationHisCompany> stateCorrelationHisCompany = stateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyById(cid,hisId);
        if(stateCorrelationHisCompany.isPresent()){
            Optional<YearMonthHistoryItem> itemToBeUpdate = stateCorrelationHisCompany.get().getHistory().stream()
                    .filter(h -> h.identifier().equals(hisId)).findFirst();
            if(itemToBeUpdate.isPresent()){
                stateCorrelationHisCompany.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start,end));
                stateCorrelationHisCompanyRepository.add(cid,itemToBeUpdate.get());
                this.updateItemBefore(cid,itemToBeUpdate.get(),stateCorrelationHisCompany.get());
            }
        }
    }

    private void updateItemBefore( String cId, YearMonthHistoryItem item, StateCorrelationHisCompany stateCorrelationHisCompany){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisCompany.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorrelationHisCompanyRepository.update(cId, itemToBeUpdated.get());
    }
}
