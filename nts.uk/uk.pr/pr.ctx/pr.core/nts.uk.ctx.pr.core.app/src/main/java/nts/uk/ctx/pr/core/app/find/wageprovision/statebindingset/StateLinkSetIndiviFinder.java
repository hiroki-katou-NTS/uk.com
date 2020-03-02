package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisIndiviRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetIndivi;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
* 明細書紐付け設定（個人）: Finder
*/
public class StateLinkSetIndiviFinder {

    @Inject
    private StateCorreHisIndiviRepository stateCorreHisIndiviRepository;

    @Inject
    private StatementLayoutRepository statementLayout;

    public StateLinkSetIndiviDto getStatementLinkingSetting(String empId, String hisId, int start){
        String cId = AppContexts.user().companyId();
        Optional<StateLinkSetIndivi> statementLinkingSetting = stateCorreHisIndiviRepository.getStateLinkSettingIndividualById(empId, hisId);
        if(!statementLinkingSetting.isPresent()) {
            return null;
        }
        List<StatementLayout> listStatementLayout = statementLayout.getStatement(cId, start);
        return StateLinkSetIndiviDto.fromDomain(statementLinkingSetting.get(), listStatementLayout);
    }

}
