package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface TempCareDataRepository {
	
	/**
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<TempCareData> findByEmpIdInPeriod(String employeeId, GeneralDate startDate, GeneralDate endDate);

}
