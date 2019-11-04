package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;

import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfo;

import java.util.Optional;
import java.util.List;

/**
* 雇用保険届作成設定
*/
public interface EmpInsReportSettingRepository {

    Optional<EmpInsReportSetting> getEmpInsReportSettingById(String cid, String userId);
    void update(EmpInsReportSetting domain);

}
