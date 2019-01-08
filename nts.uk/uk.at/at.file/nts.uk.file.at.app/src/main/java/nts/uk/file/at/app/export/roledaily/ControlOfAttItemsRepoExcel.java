package nts.uk.file.at.app.export.roledaily;

import java.util.Map;

public interface ControlOfAttItemsRepoExcel {
	//key attID
	public Map<Integer, ControlOfAttendanceItemsDtoExcel> getAllByCompanyId(String companyId);
}
