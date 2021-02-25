package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;

public interface OvertimeAppSetRepository {
    Optional<OvertimeAppSet> findSettingByCompanyId(String companyId);
    void saveOvertimeAppSet(OvertimeAppSet overtimeAppSet, OtWorkAppReflect overtimeWorkAppReflect);
    List<OvertimeQuotaSetUse> getOvertimeQuotaSetting(String companyId);
    Optional<OvertimeQuotaSetUse> getOvertimeQuotaSetting(String companyId, OvertimeAppAtr overtimeAppAtr, FlexWorkAtr flexWorkAtr);
    void saveOvertimeQuotaSet(String companyId, OvertimeQuotaSetUse overtimeQuotaSet);
}
