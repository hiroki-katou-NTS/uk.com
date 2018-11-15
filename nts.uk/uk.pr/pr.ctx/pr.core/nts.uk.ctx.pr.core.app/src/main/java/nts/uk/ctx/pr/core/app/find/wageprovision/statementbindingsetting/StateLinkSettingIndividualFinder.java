package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingIndividual;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingIndividualRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 明細書紐付け設定（個人）: Finder
*/
public class StateLinkSettingIndividualFinder {

    @Inject
    private StateLinkSettingIndividualRepository stateLinkSettingIndividual;

    @Inject
    private StatementLayoutRepository statementLayout;

    public List<StateLinkSettingIndividualDto> getAllStateLinkSettingIndividual(){
        return stateLinkSettingIndividual.getAllStateLinkSettingIndividual().stream().map(item -> StateLinkSettingIndividualDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    public StateLinkSettingIndividualDto  getStatementLinkingSetting(String empId, int start){
        Optional<StateLinkSettingIndividual> statementLinkingSetting = stateLinkSettingIndividual.getStateLinkSettingIndividualById(empId);
        if(!statementLinkingSetting.isPresent()) {
            return null;
        }
        String cId = AppContexts.user().companyId();
        List<StatementLayout> listStatementLayout = statementLayout.getStatement(cId, start);
        return StateLinkSettingIndividualDto.fromDomain(statementLinkingSetting.get(), listStatementLayout);
    }



}
