package nts.uk.file.com.app.maintenance;

import java.util.List;


public interface MaintenanceLayoutExportRepository {
	
	List<MaintenanceLayoutData> getAllMaintenanceLayout(String companyId, String contractCd);

}
