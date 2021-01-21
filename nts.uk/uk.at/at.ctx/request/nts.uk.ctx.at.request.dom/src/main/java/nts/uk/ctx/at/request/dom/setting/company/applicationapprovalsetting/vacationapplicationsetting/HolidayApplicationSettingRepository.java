package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;

import java.util.Optional;

public interface HolidayApplicationSettingRepository {
    Optional<HolidayApplicationSetting> findSettingByCompanyId(String companyId);

    void save(String companyId, HolidayApplicationSetting setting, VacationApplicationReflect reflect);
}
