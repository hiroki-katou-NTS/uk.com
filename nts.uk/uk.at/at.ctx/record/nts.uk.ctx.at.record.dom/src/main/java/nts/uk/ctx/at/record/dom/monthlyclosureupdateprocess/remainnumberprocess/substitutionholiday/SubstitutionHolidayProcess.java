package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.calculateremainnum.RemainSubstitutionHolidayCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.deletetempdata.SubstitutionTempDataDeleting;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.updateremainnum.RemainSubstitutionHolidayUpdating;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;

/**
 * 
 * @author HungTT - <<Work>> 振休処理
 *
 */

@Stateless
public class SubstitutionHolidayProcess {

	@Inject
	private RemainSubstitutionHolidayCalculation remainHolidayCalculation;
	
	@Inject
	private RemainSubstitutionHolidayUpdating remainHolidayUpdate;
	
	@Inject
	private SubstitutionTempDataDeleting tempDataDelete;
	 
	//振休処理
	public void substitutionHolidayProcess(AggrPeriodEachActualClosure period, String empId) {
		AbsRecRemainMngOfInPeriod output = remainHolidayCalculation.calculateRemainHoliday(period, empId);
		remainHolidayUpdate.updateRemainSubstitutionHoliday(output.getLstAbsRecMng(),period, empId);
		tempDataDelete.deleteTempSubstitutionData(period, empId);
	}
	
}
