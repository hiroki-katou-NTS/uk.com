package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import java.util.Optional;

/**
* 明細書利用単位設定
*/
public interface StateUseUnitSetRepository {

    Optional<StateUseUnitSet> getStateUseUnitSettingById(String cid);

    void add(StateUseUnitSet domain);

    void update(StateUseUnitSet domain);

    void remove(String cid);

}
