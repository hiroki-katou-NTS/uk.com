package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisIndividual;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisIndividualRepository;

@Stateless
/**
* 明細書紐付け履歴（個人）: Finder
*/
public class StateCorrelationHisIndividualFinder {

    @Inject
    private StateCorrelationHisIndividualRepository finderHisIndividual;

    public List<StateCorrelationHisIndividualDto> getLinkingHistoryIndividual(String emplId){
        StateCorrelationHisIndividual  stateCorrelationHisIndividual = finderHisIndividual.getStateCorrelationHisIndividualByEmpId(emplId).orElse(new StateCorrelationHisIndividual(emplId, new ArrayList<>()));
        return StateCorrelationHisIndividualDto.fromDomain(stateCorrelationHisIndividual);
    }

}
