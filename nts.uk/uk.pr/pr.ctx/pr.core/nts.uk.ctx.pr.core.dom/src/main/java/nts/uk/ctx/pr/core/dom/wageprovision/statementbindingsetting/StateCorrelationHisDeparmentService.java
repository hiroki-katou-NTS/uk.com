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
public class StateCorrelationHisDeparmentService {

    @Inject
    private StateCorrelationHisDeparmentRepository stateCorrelationHisDeparmentRepository;

    public void addOrUpdate(String cid, String hisId,YearMonth start, YearMonth end, int mode, StateLinkSettingDate stateLinkSettingDate, List<StateLinkSettingMaster> stateLinkSettingMaster){
        if(mode == RegisterMode.NEW.value){
            this.addStateCorrelationHisDeparment(cid,hisId,start,end);
            stateCorrelationHisDeparmentRepository.addAll(cid,stateLinkSettingMaster,start.v(),end.v(),stateLinkSettingDate.getDate());
        }else if(mode == RegisterMode.UPDATE.value){
            stateCorrelationHisDeparmentRepository.removeAll(cid,hisId);
            stateCorrelationHisDeparmentRepository.addAll(cid,stateLinkSettingMaster,start.v(),end.v(),stateLinkSettingDate.getDate());
        }
    }

    public void addStateCorrelationHisDeparment(String cid, String hisID, YearMonth start, YearMonth end){
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(hisID, new YearMonthPeriod(start, end));
        StateCorrelationHisDeparment stateCorrelationHisDeparment = new StateCorrelationHisDeparment(cid,new ArrayList<>());
        Optional<StateCorrelationHisDeparment> oStateCorrelationHisDeparment = stateCorrelationHisDeparmentRepository.getStateCorrelationHisDeparmentById(cid);
        if(oStateCorrelationHisDeparment.isPresent()){
            stateCorrelationHisDeparment = oStateCorrelationHisDeparment.get();
        }

        stateCorrelationHisDeparment.add(yearMonthItem);
        this.updateItemBefore(cid,yearMonthItem,stateCorrelationHisDeparment);
    }

    private void updateItemBefore(String cId, YearMonthHistoryItem item, StateCorrelationHisDeparment stateCorrelationHisDeparment){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisDeparment.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }

        stateCorrelationHisDeparmentRepository.update(cId, itemToBeUpdated.get());
    }
}
