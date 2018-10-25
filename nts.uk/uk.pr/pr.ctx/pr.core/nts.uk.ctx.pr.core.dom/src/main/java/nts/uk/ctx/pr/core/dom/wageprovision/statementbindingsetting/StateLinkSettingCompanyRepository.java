package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;
import java.util.List;

/**
* 明細書紐付け設定（会社）
*/
public interface StateLinkSettingCompanyRepository {

    Optional<StateLinkSettingCompany> getStateLinkSettingCompanyById(String hisId);

    void add(StateLinkSettingCompany domain);

    void update(StateLinkSettingCompany domain);

    void remove(String hisId);

}
