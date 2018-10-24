package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;
import java.util.List;

/**
* 明細書利用単位設定
*/
public interface StateUseUnitSettingRepository
{

    List<StateUseUnitSetting> getAllStateUseUnitSetting();

    Optional<StateUseUnitSetting> getStateUseUnitSettingById(String cid);

    void add(StateUseUnitSetting domain);

    void update(StateUseUnitSetting domain);

    void remove(String cid);

}
