package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;

import java.util.Optional;

public interface HolidayWorkAppSetRepository {
    Optional<HolidayWorkAppSet> findSettingByCompany(String companyId);

    void save(String companyId, HolidayWorkAppSet setting, HdWorkAppReflect reflect);
}
