package nts.uk.ctx.at.request.ac.record.remainingnumber.reserveleavemanage;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.reserveleavemaneger.ReserveLeaveManagerImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.reserveleavemaneger.NumberReserYearRemain;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.reserveleavemaneger.ReserveLeaveManagerApdater;

@Stateless
public class ReserveLeaveManagerApdaterImpl implements ReserveLeaveManagerApdater {

	@Override
	public ReserveLeaveManagerImport getReserveLeaveManager(String employeeID, GeneralDate referDate) {
		ReserveLeaveManagerImport reserveLeaveManager = new ReserveLeaveManagerImport();
		reserveLeaveManager.setNumber(new NumberReserYearRemain(5.00));
		return reserveLeaveManager;
	}

}
