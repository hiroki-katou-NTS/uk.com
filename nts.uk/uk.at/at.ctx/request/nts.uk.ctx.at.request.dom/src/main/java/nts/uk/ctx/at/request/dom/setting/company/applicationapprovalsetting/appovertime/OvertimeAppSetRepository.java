package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;

import java.util.List;
import java.util.Optional;

public interface OvertimeAppSetRepository {
    Optional<OvertimeAppSet> findSettingByCompanyId(String companyId);
    Optional<OtWorkAppReflect> findReflectByCompanyId(String companyId);
    void saveOvertimeAppSet(OvertimeAppSet overtimeAppSet, OtWorkAppReflect overtimeWorkAppReflect);
    List<OvertimeQuotaSetUse> getOvertimeQuotaSetting(String companyId);
    void saveOvertimeQuotaSet(String companyId, List<OvertimeQuotaSetUse> overtimeQuotaSet);
}
