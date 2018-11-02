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
    private StateLinkSettingDateRepository stateLinkSettingDateRepository;

    @Inject
    private StateLinkSettingMasterRepository stateLinkSettingMasterRepository;

    @Inject
    private StateCorrelationHisDeparmentRepository stateCorrelationHisDeparmentRepository;

    public void addOrUpdate(String cid, String hisId,YearMonth start, YearMonth end, int mode, StateLinkSettingDate stateLinkSettingDate, List<StateLinkSettingMaster> stateLinkSettingMaster){
        if(mode == RegisterMode.NEW.value){
            stateLinkSettingDateRepository.add(stateLinkSettingDate);
            stateLinkSettingMasterRepository.addAll(stateLinkSettingMaster);
            this.addStateCorrelationHisDeparment(cid,hisId,start,end);
        }else if(mode == RegisterMode.UPDATE.value){
            stateLinkSettingDateRepository.update(stateLinkSettingDate);
            stateLinkSettingMasterRepository.updateAll(stateLinkSettingMaster);
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
        stateCorrelationHisDeparmentRepository.add(cid,yearMonthItem);
        this.updateItemBefore(cid,yearMonthItem,stateCorrelationHisDeparment);
    }

    private void updateItemBefore( String cId, YearMonthHistoryItem item, StateCorrelationHisDeparment stateCorrelationHisDeparment){
        Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorrelationHisDeparment.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        stateCorrelationHisDeparmentRepository.update(cId, itemToBeUpdated.get());
    }
}
