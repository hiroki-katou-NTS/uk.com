package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDateRepository;


/**
* 明細書紐付け設定（マスタ基準日）: Finder
*/
@Stateless
public class StateLinkSettingDateFinder {

    @Inject
    private StateLinkSettingDateRepository finder;

    public Optional<StateLinkSettingDateDto> getStateLinkSettingDateById(String hisId){
        Optional<StateLinkSettingDate>  stateLinkSettingDate = finder.getStateLinkSettingDateById(hisId);
        if(stateLinkSettingDate.isPresent()){
            return Optional.of(StateLinkSettingDateDto.fromDomain(stateLinkSettingDate.get()));
        }
        return Optional.empty();
    }

}
