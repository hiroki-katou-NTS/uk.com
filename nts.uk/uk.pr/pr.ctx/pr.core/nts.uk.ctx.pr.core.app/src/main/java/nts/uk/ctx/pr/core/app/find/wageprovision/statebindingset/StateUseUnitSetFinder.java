package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateUseUnitSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateUseUnitSetRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;


/**
* 明細書利用単位設定: Finder
*/
@Stateless
public class StateUseUnitSetFinder {

    @Inject
    private StateUseUnitSetRepository finder;

    public Optional<StateUseUnitSetDto> getStateUseUnitSettingById(String cid){
        Optional<StateUseUnitSet> stateUseUnitSetting = finder.getStateUseUnitSettingById(cid);
        if(stateUseUnitSetting.isPresent()){
            return Optional.of(StateUseUnitSetDto.fromDomain(stateUseUnitSetting.get()));
        }
        return Optional.empty();
    }



}
