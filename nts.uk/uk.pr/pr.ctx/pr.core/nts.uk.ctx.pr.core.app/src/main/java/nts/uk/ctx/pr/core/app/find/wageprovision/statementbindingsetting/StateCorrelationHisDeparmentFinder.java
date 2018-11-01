package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisDeparment;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisDeparmentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
* 明細書紐付け履歴（部門）: Finder
*/
@Stateless
public class StateCorrelationHisDeparmentFinder {

    @Inject
    private StateCorrelationHisDeparmentRepository finder;

    public List<StateCorrelationHisDeparmentDto> getStateCorrelationHisDeparmentById(String cid){
        Optional<StateCorrelationHisDeparment> stateCorrelationHisDeparment = finder.getStateCorrelationHisDeparmentById(cid);
        List<StateCorrelationHisDeparmentDto> stateCorrelationHisDeparmentDto = new ArrayList<>();
        if(stateCorrelationHisDeparment.isPresent()){
            stateCorrelationHisDeparmentDto = StateCorrelationHisDeparmentDto.fromDomain(cid,stateCorrelationHisDeparment.get());
        }
        return stateCorrelationHisDeparmentDto;
    }

}
