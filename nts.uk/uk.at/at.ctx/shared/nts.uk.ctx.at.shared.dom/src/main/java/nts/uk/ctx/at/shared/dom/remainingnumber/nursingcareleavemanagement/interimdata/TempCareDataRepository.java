package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.interimdata;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface TempCareDataRepository {
	
	/**
	 * Find temp care data by emp id.
	 *
	 * @param employeeId the employee id
	 * @return the list
	 */
	public List<TempCareData> findTempCareDataByEmpId(String employeeId);
	
	/**
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<TempCareData> findByEmpIdInPeriod(String employeeId, GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * Gets the temp care data info.
	 *
	 * @param employeeId the employee id
	 * @param ymd the ymd
	 * @return the temp care data info
	 */
	public Optional<TempCareData> getTempCareDataInfo(String employeeId, GeneralDate ymd);
	
	/**
	 * Removes the temp care date.
	 *
	 * @param employeeId the employee id
	 * @param ymd the ymd
	 */
	public void removeTempCareDate(String employeeId,GeneralDate ymd);
	
	/**
	 * Update temp care date.
	 *
	 * @param tempCareDataUpdate the temp care data update
	 */
	public void updateTempCareDate(TempCareData tempCareDataUpdate);
	

}
