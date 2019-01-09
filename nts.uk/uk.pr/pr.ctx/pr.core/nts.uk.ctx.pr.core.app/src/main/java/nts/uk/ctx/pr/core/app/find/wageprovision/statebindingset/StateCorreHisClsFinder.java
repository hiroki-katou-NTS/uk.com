package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.uk.ctx.pr.core.dom.adapter.employee.classification.SysClassificationAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisCls;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisClsRepository;
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

    @Inject
    private SysClassificationAdapter classificationAdapter;

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

    public List<ClassificationImportDto> getAllClassificationByCid() {
        String cid = AppContexts.user().companyId();
        return classificationAdapter.getClassificationByCompanyId(cid).stream().map(i -> new ClassificationImportDto(i)).collect(Collectors.toList());
    }
}
