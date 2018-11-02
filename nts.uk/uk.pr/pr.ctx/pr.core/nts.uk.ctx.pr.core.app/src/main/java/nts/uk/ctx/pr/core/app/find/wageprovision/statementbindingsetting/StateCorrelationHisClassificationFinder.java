package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassification;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassificationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMasterRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 明細書紐付け履歴（分類）: Finder
*/
public class StateCorrelationHisClassificationFinder {

    @Inject
    private StateCorrelationHisClassificationRepository classificationFinder;

    @Inject
    private StateLinkSettingMasterRepository masterFinder;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;

    public List<StateCorrelationHisClassificationDto> getStateCorrelationHisClassificationByCid(){
        String cId = AppContexts.user().companyId();
        Optional<StateCorrelationHisClassification> hisClassification = classificationFinder.getStateCorrelationHisClassificationByCid(cId);
        if(!hisClassification.isPresent()) {
            return null;
        }
        return StateCorrelationHisClassificationDto.fromDomain(hisClassification.get());
    }

    public List<StateLinkSettingMasterDto> getStateLinkSettingMaster(String hisId, int startYearMonth){
        String cId = AppContexts.user().companyId();
        List<StatementLayout> statementLayout = statementLayoutFinder.getStatement(cId, startYearMonth);
        return masterFinder.getStateLinkSettingMasterByHisId(hisId).stream()
                .map(i -> StateLinkSettingMasterDto.fromDomain(i, statementLayout))
                .collect(Collectors.toList());

    }


}
