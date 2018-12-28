package nts.uk.file.at.app.export.worktime;

import java.util.List;

public interface WorkTimeReportRepository {
	List<Object[]> findWorkTimeNormal(String companyId);
	List<Object[]> findWorkTimeFlow(String companyId);
	List<Object[]> findWorkTimeFlex(String companyId);
}
