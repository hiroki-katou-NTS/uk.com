package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;
import java.util.List;

/**
* 明細書紐付け設定（マスタ）
*/
public interface StateLinkSettingMasterRepository {

    List<StateLinkSettingMaster> getStateLinkSettingMasterByHisId(String hisId);

    Optional<StateLinkSettingMaster> getStateLinkSettingMasterById(String hisId, String masterCode);

    void add(StateLinkSettingMaster domain);

    void update(StateLinkSettingMaster domain);

    void remove(String hisId, String masterCode);

}
