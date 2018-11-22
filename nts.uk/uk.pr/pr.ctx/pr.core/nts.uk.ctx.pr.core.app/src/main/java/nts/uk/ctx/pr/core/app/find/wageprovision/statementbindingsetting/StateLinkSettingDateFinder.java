package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisPositionRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDate;
import nts.uk.shr.com.context.AppContexts;


/**
* 明細書紐付け設定（マスタ基準日）: Finder
*/
@Stateless
public class StateLinkSettingDateFinder {

    @Inject
    private StateCorrelationHisPositionRepository finder;

    public Optional<StateLinkSettingDateDto> getStateLinkSettingDateById(String hisId){
        String cId = AppContexts.user().companyId();
        Optional<StateLinkSettingDate>  stateLinkSettingDate = finder.getStateLinkSettingDateById(cId,hisId);
        if(stateLinkSettingDate.isPresent()){
            return Optional.of(StateLinkSettingDateDto.fromDomain(stateLinkSettingDate.get()));
        }
        return Optional.empty();
    }

}
