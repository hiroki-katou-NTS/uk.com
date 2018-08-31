package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.calculateremainnum.RemainAnnualLeaveCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.deletetempdata.AnnualLeaveTempDataDeleting;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.deletetempreserveannual.RsvAnnualLeaveTempDataDeleting;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.updateremainnum.RemainAnnualLeaveUpdating;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.updatereserveannual.RemainReserveAnnualLeaveUpdating;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;

/**
 * 
 * @author HungTT - <<Work>> 年休処理
 *
 */

@Stateless
public class AnnualLeaveProcess {

	@Inject
	private RemainAnnualLeaveCalculation remainHolidayCalculation;

	@Inject
	private RemainAnnualLeaveUpdating remainHolidayUpdating;

	@Inject
	private AnnualLeaveTempDataDeleting tmpAnnualLeaveDeleting;

	@Inject
	private RemainReserveAnnualLeaveUpdating rsvRemainAnnualLeaveUpdating;

	@Inject
	private RsvAnnualLeaveTempDataDeleting tmpRsvAnnualLeaveDeleting;

	// 年休残数処理
	public void annualHolidayProcess(AggrPeriodEachActualClosure period, String empId) {
		AggrResultOfAnnAndRsvLeave output = remainHolidayCalculation.calculateRemainAnnualHoliday(period, empId);
		if (output.getAnnualLeave().isPresent())
			remainHolidayUpdating.updateRemainAnnualLeave(output.getAnnualLeave().get(), period, empId);
		tmpAnnualLeaveDeleting.deleteTempAnnualLeaveData(empId, period.getPeriod());
		if (output.getReserveLeave().isPresent())
			rsvRemainAnnualLeaveUpdating.updateReservedAnnualLeaveRemainNumber(output.getReserveLeave().get(), period, empId);
		tmpRsvAnnualLeaveDeleting.deleteTempRsvAnnualLeaveData(empId, period.getPeriod());
	}

}
