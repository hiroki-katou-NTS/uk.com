package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.Map;

public interface ControlOfAttItemsRepoExcel {
	//key attID
	public Map<Integer, ControlOfAttendanceItemsDtoExcel> getAllByCompanyId(String companyId);
}
