package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisDepar;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisDeparRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetDate;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
* 明細書紐付け履歴（部門）: Finder
*/
@Stateless
public class StateCorreHisDeparFinder {

    @Inject
    private StateCorreHisDeparRepository finder;

    public List<StateCorreHisDeparDto> getStateCorrelationHisDeparmentById(String cid){
        Optional<StateCorreHisDepar> stateCorrelationHisDeparment = finder.getStateCorrelationHisDeparmentById(cid);
        List<StateCorreHisDeparDto> stateCorreHisDeparDto = new ArrayList<>();
        if(stateCorrelationHisDeparment.isPresent()){
            stateCorreHisDeparDto = StateCorreHisDeparDto.fromDomain(cid,stateCorrelationHisDeparment.get());
        }
        return stateCorreHisDeparDto;
    }

    public StateLinkSetDateDto getDateBase(String hisId){
        String cId = AppContexts.user().companyId();
        Optional<StateLinkSetDate>  stateLinkSettingDate = finder.getStateLinkSettingDateById(cId,hisId);
        if(stateLinkSettingDate.isPresent()) {
            return StateLinkSetDateDto.fromDomain(stateLinkSettingDate.get());
        }
        return null;
    }

}
