package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.calculateremainnum.RemainSubstitutionHolidayCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.deletetempdata.SubstitutionTempDataDeleting;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.updateremainnum.RemainSubstitutionHolidayUpdating;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;

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
	 
	/**
	 * 振休処理
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 */
	public void substitutionHolidayProcess(AggrPeriodEachActualClosure period, String empId,
			Map<GeneralDate, DailyInterimRemainMngData> interimRemainMngMap) {
		
		// 振休残数計算
		AbsRecRemainMngOfInPeriod output = remainHolidayCalculation.calculateRemainHoliday(
				period, empId, interimRemainMngMap);
		
		// 振休残数更新
		remainHolidayUpdate.updateRemainSubstitutionHoliday(output.getLstAbsRecMng(),period, empId);
		
		// 振休暫定データ削除
		tempDataDelete.deleteTempSubstitutionData(period, empId);
	}
}
