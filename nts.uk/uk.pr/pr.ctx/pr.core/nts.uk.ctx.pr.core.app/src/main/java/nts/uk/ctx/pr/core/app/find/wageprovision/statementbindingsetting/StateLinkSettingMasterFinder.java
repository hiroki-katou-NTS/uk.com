package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMasterRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 明細書紐付け設定（マスタ）: Finder
*/
public class StateLinkSettingMasterFinder
{

    @Inject
    private StateLinkSettingMasterRepository finder;

    public List<StateLinkSettingMasterDto> getStateLinkSettingMasterByHisId(String hisId){
        return finder.getStateLinkSettingMasterByHisId(hisId).stream()
                .map(i -> StateLinkSettingMasterDto.fromDomain(i))
                .collect(Collectors.toList());
    }

}
