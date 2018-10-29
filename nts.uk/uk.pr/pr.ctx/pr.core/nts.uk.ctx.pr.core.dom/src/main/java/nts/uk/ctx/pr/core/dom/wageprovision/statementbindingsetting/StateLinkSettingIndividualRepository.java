package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;
import java.util.List;

/**
* 明細書紐付け設定（個人）
*/
public interface StateLinkSettingIndividualRepository
{

    List<StateLinkSettingIndividual> getAllStateLinkSettingIndividual();

    Optional<StateLinkSettingIndividual> getStateLinkSettingIndividualById(String hisId);

    void add(StateLinkSettingIndividual domain);

    void update(StateLinkSettingIndividual domain);

    void remove(String hisId);

}
