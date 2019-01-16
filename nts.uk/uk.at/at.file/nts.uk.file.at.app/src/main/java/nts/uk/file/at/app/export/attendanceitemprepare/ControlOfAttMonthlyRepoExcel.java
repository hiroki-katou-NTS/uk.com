package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.Map;
/**
 * 
 * @author Hoidd
 *
 */
public interface ControlOfAttMonthlyRepoExcel {
	//key attID
	public Map<Integer, ControlOfAttMonthlyDtoExcel> getAllByCompanyId(String companyId);
}
