package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting;

import java.util.Optional;

public interface AppStampSettingRepository {
    Optional<AppStampSetting> findByCompanyId(String companyId);
}
