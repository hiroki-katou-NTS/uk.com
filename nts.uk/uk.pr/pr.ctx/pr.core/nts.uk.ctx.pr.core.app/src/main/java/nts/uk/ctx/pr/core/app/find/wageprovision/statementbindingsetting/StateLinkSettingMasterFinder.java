package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
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
    private StatementLayoutRepository statementLayoutFinder;

    public List<StateLinkSettingMasterDto> getStateLinkSettingMaster(int startYearMonth, List<StateLinkSettingMaster> listStateLinkSettingMaster){
        String cId = AppContexts.user().companyId();
        List<StatementLayout> statementLayout = statementLayoutFinder.getStatement(cId, startYearMonth);
        List<StateLinkSettingMasterDto> listStateLinkSettingMasterDto = listStateLinkSettingMaster.stream()
                .map(i -> StateLinkSettingMasterDto.fromDomain(i, statementLayout)).collect(Collectors.toList());
        return listStateLinkSettingMasterDto;
    }

}
