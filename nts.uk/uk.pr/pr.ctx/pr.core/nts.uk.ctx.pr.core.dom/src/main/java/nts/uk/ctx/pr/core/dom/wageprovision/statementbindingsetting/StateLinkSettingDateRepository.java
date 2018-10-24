package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;
import java.util.List;

/**
* 明細書紐付け設定（マスタ基準日）
*/
public interface StateLinkSettingDateRepository
{

    List<StateLinkSettingDate> getAllStateLinkSettingDate();

    Optional<StateLinkSettingDate> getStateLinkSettingDateById(String hisId);

    void add(StateLinkSettingDate domain);

    void update(StateLinkSettingDate domain);

    void remove(String hisId);

}
