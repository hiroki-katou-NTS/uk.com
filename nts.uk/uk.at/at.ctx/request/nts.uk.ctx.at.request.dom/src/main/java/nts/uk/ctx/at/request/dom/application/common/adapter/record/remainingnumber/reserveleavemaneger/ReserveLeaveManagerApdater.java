package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.reserveleavemaneger;

import nts.arc.time.GeneralDate;

public interface ReserveLeaveManagerApdater {
	/**
	 * Import requestList201
	 * @param employeeID
	 * @param referDate
	 * @return
	 */
	ReserveLeaveManagerImport getReserveLeaveManager(String employeeID, GeneralDate referDate);
}
