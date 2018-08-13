package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.calculateremainnum.RemainCompensatoryHolidayCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.deletetempdata.CompensatoryTempDataDeleting;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.updateremainnum.RemainCompensatoryHolidayUpdating;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;

/**
 * 
 * @author HungTT - <<Work>> 代休処理
 *
 */

@Stateless
public class CompensatoryHolidayProcess {

	@Inject
	private RemainCompensatoryHolidayCalculation remainCalculation;
	
	@Inject
	private RemainCompensatoryHolidayUpdating remainUpdate;
	
	@Inject
	private CompensatoryTempDataDeleting tempDelete;
	
	public void compensatoryHolidayProcess(AggrPeriodEachActualClosure period, String empId) {
		BreakDayOffRemainMngOfInPeriod output = this.remainCalculation.calculateRemainCompensatory(period, empId);
		remainUpdate.updateRemainCompensatoryHoliday(output.getLstDetailData(), period, empId);
		tempDelete.deleteTempDataProcess(period, empId);
	}
	
}
