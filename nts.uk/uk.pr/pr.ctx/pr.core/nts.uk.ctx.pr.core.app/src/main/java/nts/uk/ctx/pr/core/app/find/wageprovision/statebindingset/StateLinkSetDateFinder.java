package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisPoRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetDate;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;


/**
* 明細書紐付け設定（マスタ基準日）: Finder
*/
@Stateless
public class StateLinkSetDateFinder {

    @Inject
    private StateCorreHisPoRepository finder;

    public Optional<StateLinkSetDateDto> getStateLinkSettingDateById(String hisId){
        String cId = AppContexts.user().companyId();
        Optional<StateLinkSetDate>  stateLinkSettingDate = finder.getStateLinkSettingDateById(cId,hisId);
        if(stateLinkSettingDate.isPresent()){
            return Optional.of(StateLinkSetDateDto.fromDomain(stateLinkSettingDate.get()));
        }
        return Optional.empty();
    }

}
