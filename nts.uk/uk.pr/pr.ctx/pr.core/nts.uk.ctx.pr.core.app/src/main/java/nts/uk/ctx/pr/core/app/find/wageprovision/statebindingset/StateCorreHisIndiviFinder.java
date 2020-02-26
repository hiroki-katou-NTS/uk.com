package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisIndivi;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisIndiviRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
/**
* 明細書紐付け履歴（個人）: Finder
*/
public class StateCorreHisIndiviFinder {

    @Inject
    private StateCorreHisIndiviRepository finderHisIndividual;

    public List<StateCorreHisIndiviDto> getLinkingHistoryIndividual(String emplId){
        StateCorreHisIndivi stateCorreHisIndivi = finderHisIndividual.getStateCorrelationHisIndividualByEmpId(emplId).orElse(new StateCorreHisIndivi(emplId, new ArrayList<>()));
        return StateCorreHisIndiviDto.fromDomain(stateCorreHisIndivi);
    }

}
