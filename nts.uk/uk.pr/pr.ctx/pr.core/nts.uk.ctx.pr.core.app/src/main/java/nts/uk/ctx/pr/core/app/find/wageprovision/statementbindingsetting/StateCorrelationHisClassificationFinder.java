package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassification;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassificationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.HEAD;

@Stateless
/**
* 明細書紐付け履歴（分類）: Finder
*/
public class StateCorrelationHisClassificationFinder {

    @Inject
    private StateCorrelationHisClassificationRepository classificationFinder;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;

    public List<StateCorrelationHisClassificationDto> getStateCorrelationHisClassificationByCid(){
        String cId = AppContexts.user().companyId();
        StateCorrelationHisClassification hisClassification = classificationFinder.getStateCorrelationHisClassificationByCid(cId)
                .orElse(new StateCorrelationHisClassification(cId, new ArrayList<>()));
        return StateCorrelationHisClassificationDto.fromDomain(hisClassification);
    }

    public List<StateLinkSettingMasterDto> getStateLinkSettingMaster(String hisId, int startYearMonth){
        String cId = AppContexts.user().companyId();
        List<StatementLayout> statementLayout = statementLayoutFinder.getStatement(cId, startYearMonth);
        return classificationFinder.getStateLinkSettingMasterByHisId(cId, hisId).stream()
                .map(i -> StateLinkSettingMasterDto.fromDomain(i, statementLayout))
                .collect(Collectors.toList());
    }


}
