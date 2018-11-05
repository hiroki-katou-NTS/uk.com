package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;


import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class StateCorrelationHisEmployeeService {

    @Inject
    private StateCorrelationHisEmployeeRepository stateCorrelationHisEmployeeRepository;

    @Inject
    private StateLinkSettingMasterRepository stateLinkSettingMasterRepository;

    public void addOrUpdate(String cid, String hisId,YearMonth start, YearMonth end, int mode, List<StateLinkSettingMaster> stateLinkSettingMaster){
        if(mode == RegisterMode.NEW.value){
            stateLinkSettingMasterRepository.addAll(stateLinkSettingMaster);
            this.addStateCorrelationHisEmployee(cid,hisId,start,end);
        }else if(mode == RegisterMode.UPDATE.value){
            stateLinkSettingMasterRepository.updateAll(stateLinkSettingMaster);
        }
    }

    public void addStateCorrelationHisEmployee(String cid, String hisID, YearMonth start, YearMonth end){
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(hisID, new YearMonthPeriod(start, end));
        StateCorrelationHisEmployee stateCorrelationHisEmployee = new StateCorrelationHisEmployee(cid,new ArrayList<>());
        Optional<StateCorrelationHisEmployee> oStateCorrelationHisEmployee = stateCorrelationHisEmployeeRepository.getStateCorrelationHisEmployeeById(cid);
        if(oStateCorrelationHisEmployee.isPresent()){
            stateCorrelationHisEmployee = oStateCorrelationHisEmployee.get();
        }
        stateCorrelationHisEmployee.add(yearMonthItem);
        stateCorrelationHisEmployeeRepository.add(cid,yearMonthItem);
        this.updateItemBefore(cid,yearMonthItem,stateCorrelationHisEmployee);
    }

    private void updateItemBefore(String cId, YearMonthHistoryItem item, StateCorrelationHisEmployee stateCorrelationHisEmployee){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisEmployee.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorrelationHisEmployeeRepository.update(cId, itemToBeUpdated.get());
    }
}
