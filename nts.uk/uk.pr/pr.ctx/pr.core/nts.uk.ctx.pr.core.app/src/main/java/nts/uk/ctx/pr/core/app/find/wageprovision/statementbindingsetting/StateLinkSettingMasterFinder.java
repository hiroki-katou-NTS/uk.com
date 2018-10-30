package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayoutRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMasterRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
* 明細書紐付け設定（マスタ）: Finder
*/
@Stateless
public class StateLinkSettingMasterFinder {

    @Inject
    private StateLinkSettingMasterRepository masterFinder;

    @Inject
    private SpecificationLayoutRepository specificationLayoutFinder;

    public List<StateLinkSettingMasterDto> getStateLinkSettingMasterByHisId(String hisId, int startYearMonth){
        String cId = AppContexts.user().companyId();
        List<SpecificationLayout> specificationLayout = specificationLayoutFinder.getSpecCode(cId, hisId, startYearMonth);
        return masterFinder.getStateLinkSettingMasterByHisId(hisId).stream()
                .map(i -> StateLinkSettingMasterDto.fromDomain(i, specificationLayout))
                .collect(Collectors.toList());

    }

}
