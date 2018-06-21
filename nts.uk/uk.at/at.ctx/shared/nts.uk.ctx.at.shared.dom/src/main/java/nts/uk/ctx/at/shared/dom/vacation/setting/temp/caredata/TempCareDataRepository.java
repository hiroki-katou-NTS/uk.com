package nts.uk.ctx.at.shared.dom.vacation.setting.temp.caredata;

import java.util.List;

public interface TempCareDataRepository {
	
	/**
	 * Find temp care data by emp id.
	 *
	 * @param employeeId the employee id
	 * @return the list
	 */
	public List<TempCareData> findTempCareDataByEmpId(String employeeId);

}
