package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkApplicationReflect;

import java.util.Optional;

public interface HolidayWorkAppSetRepository {
    Optional<HolidayWorkAppSet> findSettingByCompany(String companyId);

    Optional<HdWorkApplicationReflect> findReflectByCompany(String companyId);

    void save(String companyId, HolidayWorkAppSet setting, HdWorkApplicationReflect reflect);
}
