package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting;

import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.substituteworkapplication.SubstituteWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp.SubstituteLeaveAppReflect;

import java.util.Optional;

public interface SubstituteHdWorkAppSetRepository {
    Optional<SubstituteHdWorkAppSet> findSettingByCompany(String companyId);
    void save(SubstituteHdWorkAppSet setting, SubstituteLeaveAppReflect subLeaveReflect, SubstituteWorkAppReflect subWorkReflect);
}
