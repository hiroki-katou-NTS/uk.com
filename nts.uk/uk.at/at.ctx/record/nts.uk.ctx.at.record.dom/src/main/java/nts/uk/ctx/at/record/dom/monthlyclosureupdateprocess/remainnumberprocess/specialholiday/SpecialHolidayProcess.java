package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.calculateremainnum.RemainSpecialHoidayCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.deletetempdata.SpecialTempDataDeleting;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.updateremainnum.RemainSpecialHolidayUpdating;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.InPeriodOfSpecialLeaveResultInfor;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.shr.com.context.AppContexts;

/**
 * 特別休暇処理
 * @author shuichi_ishida
 */
public class SpecialHolidayProcess {

	/**
	 * 特別休暇処理
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 */
	public static AtomTask specialHolidayProcess(RequireM1 require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String empId,
			Map<GeneralDate, DailyInterimRemainMngData> interimRemainMngMap) {

		List<AtomTask> atomTask = new ArrayList<>();
		String companyId = AppContexts.user().companyId();

		// 暫定残数データを特別休暇に絞り込む
		List<InterimRemain> interimMng = new ArrayList<>();
		List<InterimSpecialHolidayMng> interimSpecialData = new ArrayList<>();
		for (val interimRemainMng : interimRemainMngMap.values()){
			if (interimRemainMng.getRecAbsData().size() <= 0) continue;
			if (interimRemainMng.getSpecialHolidayData().size() <= 0) continue;
			interimMng.addAll(interimRemainMng.getRecAbsData());
			interimSpecialData.addAll(interimRemainMng.getSpecialHolidayData());
		}

		// 「特別休暇」を取得する
		val specialHolidays = require.specialHoliday(companyId);
		for (val specialHoliday : specialHolidays){
			int specialLeaveCode = specialHoliday.getSpecialHolidayCode().v();
			int autoGrant = specialHoliday.getAutoGrant().value;
			// 特別休暇残数計算
			InPeriodOfSpecialLeaveResultInfor output = RemainSpecialHoidayCalculation.calculateRemainSpecial(
					require, cacheCarrier, period, empId, specialLeaveCode, interimMng, interimSpecialData);

			// 特別休暇残数更新
			atomTask.add(RemainSpecialHolidayUpdating.updateRemainSpecialHoliday(require, output,
					empId, period.getPeriod(), specialLeaveCode, autoGrant));
		}

		// 特別休暇暫定データ削除
		return AtomTask.bundle(atomTask)
				.then(SpecialTempDataDeleting.deleteTempDataProcess(require, empId, period.getPeriod()));
	}

	public static interface RequireM1 extends RemainSpecialHoidayCalculation.RequireM1,
		RemainSpecialHolidayUpdating.RequireM1, SpecialTempDataDeleting.RequireM1 {

		List<SpecialHoliday> specialHoliday(String companyId);
	}
}
