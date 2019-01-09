package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.Map;
/**
 * 
 * @author hoidd
 *
 */
public interface ControlOfAttItemsRepoExcel {
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	//key attID
	public Map<Integer, ControlOfAttendanceItemsDtoExcel> getAllByCompanyId(String companyId);
}
