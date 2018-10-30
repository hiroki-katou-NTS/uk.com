package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateUseUnitSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateUseUnitSettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;


/**
* 明細書利用単位設定: Finder
*/
@Stateless
public class StateUseUnitSettingFinder
{

    @Inject
    private StateUseUnitSettingRepository finder;

    public Optional<StateUseUnitSettingDto> getStateUseUnitSettingById(String cid){
        Optional<StateUseUnitSetting> stateUseUnitSetting = finder.getStateUseUnitSettingById(cid);
        if(stateUseUnitSetting.isPresent()){
            return Optional.of(StateUseUnitSettingDto.fromDomain(stateUseUnitSetting.get()));
        }
        return Optional.empty();
    }


}
