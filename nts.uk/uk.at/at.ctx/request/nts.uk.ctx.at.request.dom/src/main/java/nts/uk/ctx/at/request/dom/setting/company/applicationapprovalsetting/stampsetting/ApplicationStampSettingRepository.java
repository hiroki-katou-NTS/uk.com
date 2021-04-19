package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;

public interface ApplicationStampSettingRepository {
    Optional<AppStampSetting> findSettingByCompanyId(String companyId);

    void save(String companyId, AppStampSetting setting, StampAppReflect reflect);
}
