package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorreHisCls;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorreHisClsRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* 明細書紐付け履歴（分類）: Finder
*/
public class StateCorreHisClsFinder {

    @Inject
    private StateCorreHisClsRepository classificationFinder;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;

    public List<StateCorreHisClsDto> getStateCorrelationHisClassificationByCid(){
        String cId = AppContexts.user().companyId();
        StateCorreHisCls hisClassification = classificationFinder.getStateCorrelationHisClassificationByCid(cId)
                .orElse(new StateCorreHisCls(cId, new ArrayList<>()));
        return StateCorreHisClsDto.fromDomain(hisClassification);
    }

    public List<StateLinkSetMasterDto> getStateLinkSettingMaster(String hisId, int startYearMonth){
        String cId = AppContexts.user().companyId();
        List<StatementLayout> statementLayout = statementLayoutFinder.getStatement(cId, startYearMonth);
        return classificationFinder.getStateLinkSettingMasterByHisId(cId, hisId).stream()
                .map(i -> StateLinkSetMasterDto.fromDomain(i, statementLayout))
                .collect(Collectors.toList());
    }


}
