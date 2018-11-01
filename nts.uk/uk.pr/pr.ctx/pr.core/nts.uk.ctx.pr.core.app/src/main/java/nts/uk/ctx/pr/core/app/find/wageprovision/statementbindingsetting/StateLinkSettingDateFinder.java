package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDateRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;


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
