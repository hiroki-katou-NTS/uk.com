package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.calculateremainnum.RemainCompensatoryHolidayCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.deletetempdata.CompensatoryTempDataDeleting;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.updateremainnum.RemainCompensatoryHolidayUpdating;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
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
	
	/**
	 * 代休処理
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 */
	public void compensatoryHolidayProcess(AggrPeriodEachActualClosure period, String empId,
			Map<GeneralDate, DailyInterimRemainMngData> interimRemainMngMap) {
		
		// 代休計算処理
		BreakDayOffRemainMngOfInPeriod output = this.remainCalculation.calculateRemainCompensatory(
				period, empId, interimRemainMngMap);
		
		// 代休残数更新
		remainUpdate.updateRemainCompensatoryHoliday(output.getLstDetailData(), period, empId);
		
		// 代休暫定データ削除
		tempDelete.deleteTempDataProcess(period, empId);
	}
}
