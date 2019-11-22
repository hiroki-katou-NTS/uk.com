package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;

import java.util.Optional;

/**
 * 雇用保険届作成設定
 */
public interface EmpInsReportSettingRepository {

    Optional<EmpInsReportSetting> getEmpInsReportSettingById(String cid, String userId);

    void insert(EmpInsReportSetting domain);

    void update(EmpInsReportSetting domain);

}
