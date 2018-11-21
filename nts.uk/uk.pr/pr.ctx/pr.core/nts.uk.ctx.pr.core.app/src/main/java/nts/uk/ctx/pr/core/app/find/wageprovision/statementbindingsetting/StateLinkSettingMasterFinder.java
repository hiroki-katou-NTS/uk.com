package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDateRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMasterRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 明細書紐付け設定（マスタ）: Finder
*/
public class StateLinkSettingMasterFinder {

    @Inject
    private StateLinkSettingMasterRepository masterFinder;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;

    public List<StateLinkSettingMasterDto> getStateLinkSettingMaster(String hisId, int startYearMonth){
        String cId = AppContexts.user().companyId();
        List<StatementLayout> statementLayout = statementLayoutFinder.getStatement(cId, startYearMonth);
        List<StateLinkSettingMaster>  listStateLinkSettingMaster = masterFinder.getStateLinkSettingMasterByHisId(hisId);
        List<StateLinkSettingMasterDto> listStateLinkSettingMasterDto = listStateLinkSettingMaster.stream()
                .map(i -> StateLinkSettingMasterDto.fromDomain(i, statementLayout)).collect(Collectors.toList());
        return listStateLinkSettingMasterDto;
    }

}
