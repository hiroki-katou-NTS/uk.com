package nts.uk.file.com.app.newlayout;

import java.util.List;


public interface NewLayoutExportRepository {
	List<NewLayoutExportData> getAllMaintenanceLayout(String companyId, String contractCd, int forAttendance, int forPayroll, int forPersonnel);
}
