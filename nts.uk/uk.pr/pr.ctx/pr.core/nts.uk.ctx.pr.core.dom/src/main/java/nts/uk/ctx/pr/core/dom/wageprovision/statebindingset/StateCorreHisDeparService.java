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
public class StateCorreHisDeparService {

    @Inject
    private StateCorreHisDeparRepository stateCorreHisDeparRepository;

    public void addOrUpdate(String cid, String hisId, YearMonth start, YearMonth end, int mode, StateLinkSetDate stateLinkSetDate, List<StateLinkSetMaster> stateLinkSetMaster){
        if(mode == RegisterMode.NEW.value){
            this.addStateCorrelationHisDeparment(cid,hisId,start,end);
            stateCorreHisDeparRepository.addAll(cid, stateLinkSetMaster,start.v(),end.v(), stateLinkSetDate.getDate());
        }else if(mode == RegisterMode.UPDATE.value){
            stateCorreHisDeparRepository.removeAll(cid,hisId);
            stateCorreHisDeparRepository.addAll(cid, stateLinkSetMaster,start.v(),end.v(), stateLinkSetDate.getDate());
        }
    }

    public void addStateCorrelationHisDeparment(String cid, String hisID, YearMonth start, YearMonth end){
        YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(hisID, new YearMonthPeriod(start, end));
        StateCorreHisDepar stateCorreHisDepar = new StateCorreHisDepar(cid,new ArrayList<>());
        Optional<StateCorreHisDepar> oStateCorrelationHisDeparment = stateCorreHisDeparRepository.getStateCorrelationHisDeparmentById(cid);
        if(oStateCorrelationHisDeparment.isPresent()){
            stateCorreHisDepar = oStateCorrelationHisDeparment.get();
        }

        stateCorreHisDepar.add(yearMonthItem);
        this.updateItemBefore(cid,yearMonthItem, stateCorreHisDepar);
    }

    private void updateItemBefore(String cId, YearMonthHistoryItem item, StateCorreHisDepar stateCorreHisDepar){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHisDepar.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }

        stateCorreHisDeparRepository.update(cId, itemToBeUpdated.get());
    }
}
