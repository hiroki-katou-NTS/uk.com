package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisIndividual;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisIndividualRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingIndividualRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 明細書紐付け履歴（個人）: Finder
*/
public class StateCorrelationHisIndividualFinder {

    @Inject
    private StateCorrelationHisIndividualRepository finderHisIndividual;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;

    @Inject
    private StateLinkSettingIndividualRepository stateLinkSettingIndividual;

    public List<StateCorrelationHisIndividualDto> getLinkingHistoryIndividual(String emplId){
        Optional<StateCorrelationHisIndividual>  stateCorrelationHisIndividual = finderHisIndividual.getStateCorrelationHisIndividualById(emplId);
        if(!stateCorrelationHisIndividual.isPresent()) {
            return null;
        }
        return StateCorrelationHisIndividualDto.fromDomain(stateCorrelationHisIndividual.get());
    }

}
