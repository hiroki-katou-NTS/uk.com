package nts.uk.ctx.workflow.dom.service;

import nts.arc.time.GeneralDate;

public interface DeleteHistoryCmm053Service {
	/**
	 * Delete History By Manager Setting
	 * @param startDate
	 * @param endDate
	 * @param companyId
	 * @param employeeId
	 */
	void deleteHistoryByManagerSetting(GeneralDate startDate, GeneralDate endDate, String companyId, String employeeId);
}
