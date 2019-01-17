package nts.uk.file.com.app.workplaceselection;

import java.util.List;

import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceFunction;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface WorkPlaceSelectionRepository {
	// Export Data
	//List<WorkPlaceSelectionExportData> findAllWorkPlaceSelection(String companyId, List<WorkPlaceFunction> functionNo);

	List<MasterData> getDataExport(String companyId, List<WorkPlaceFunction> workPlaceFunction, String baseDate);

}
