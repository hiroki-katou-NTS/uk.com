package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class StateCorreHisEmService {

    @Inject
    private StateCorreHisEmRepository stateCorreHisEmRepository;


    public void addOrUpdate(String cid, String hisId,YearMonth start, YearMonth end, int mode, List<StateLinkSetMaster> stateLinkSetMaster){
        if(mode == RegisterMode.NEW.value){
            this.addStateCorrelationHisEmployee(cid,hisId,start,end);
            stateCorreHisEmRepository.addAll(cid, stateLinkSetMaster,start.v(), end.v());
        }else if(mode == RegisterMode.UPDATE.value){
            stateCorreHisEmRepository.removeAll(cid,hisId);
            stateCorreHisEmRepository.addAll(cid, stateLinkSetMaster,start.v(), end.v());
        }
    }

    public void addStateCorrelationHisEmployee(String cid, String hisID, YearMonth start, YearMonth end){
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(hisID, new YearMonthPeriod(start, end));
        StateCorreHisEm stateCorreHisEm = new StateCorreHisEm(cid,new ArrayList<>());
        Optional<StateCorreHisEm> oStateCorrelationHisEmployee = stateCorreHisEmRepository.getStateCorrelationHisEmployeeById(cid);
        if(oStateCorrelationHisEmployee.isPresent()){
            stateCorreHisEm = oStateCorrelationHisEmployee.get();
        }
        stateCorreHisEm.add(yearMonthItem);
        this.updateItemBefore(cid,yearMonthItem, stateCorreHisEm);
    }

    private void updateItemBefore(String cId, YearMonthHistoryItem item, StateCorreHisEm stateCorreHisEm){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHisEm.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorreHisEmRepository.update(cId, itemToBeUpdated.get());
    }
}
