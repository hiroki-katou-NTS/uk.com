package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import java.util.Optional;

public interface HolidayApplicationSettingRepository {
    Optional<HolidayApplicationSetting> findByCompanyId(String companyId);
}
