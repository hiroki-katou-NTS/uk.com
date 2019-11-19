package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;

import java.util.Optional;

public interface EmpInsReportTxtSettingRepository {

	Optional<EmpInsReportTxtSetting> getEmpInsReportTxtSettingByUserId(String cid, String userId);

	void insert(EmpInsReportTxtSetting domain);

	void update(EmpInsReportTxtSetting domain);
}
