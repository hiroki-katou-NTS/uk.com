package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMasterRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;


/**
* 明細書紐付け設定（マスタ）: Finder
*/
@Stateless
public class StateLinkSettingMasterFinder {

    @Inject
    private StateLinkSettingMasterRepository masterFinder;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;

    public List<StateLinkSettingMasterDto> getStateLinkSettingMasterByHisId(String hisId, int startYearMonth){
        String cId = AppContexts.user().companyId();
        List<StatementLayout> statementLayout = statementLayoutFinder.getStatement(cId, startYearMonth);
        return masterFinder.getStateLinkSettingMasterByHisId(hisId).stream()
                .map(i -> StateLinkSettingMasterDto.fromDomain(i, statementLayout))
                .collect(Collectors.toList());

    }

}
