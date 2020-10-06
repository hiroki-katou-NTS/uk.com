package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting;

import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;

import java.util.Optional;

public interface ApplicationStampSettingRepository {
    Optional<AppStampSetting> findSettingByCompanyId(String companyId);

    void save(String companyId, AppStampSetting setting, StampAppReflect reflect);
}
