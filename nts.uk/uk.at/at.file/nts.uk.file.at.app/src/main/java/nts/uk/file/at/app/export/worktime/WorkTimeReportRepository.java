package nts.uk.file.at.app.export.worktime;

import java.util.List;

public interface WorkTimeReportRepository {
	List<Object[]> findWorkTimeNormal(String companyId, String langId);
	List<Object[]> findWorkTimeFlow(String companyId, String langId);
	List<Object[]> findWorkTimeFlex(String companyId, String langId);
}
