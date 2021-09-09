package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.closegetunlockedperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.AchievementAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.GetPeriodCanProcesse;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.IgnoreFlagDuringLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

/**
 * DS: 実績締めロックされない期間を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績処理.作成処理.日別作成Mgrクラス.アルゴリズム.社員の日別実績を作成する.実績締めロックされない期間を取得する.実績締めロックされない期間を取得する
 * 
 * @author tutk
 *
 */
@Stateless
public class ClosingGetUnlockedPeriod {

	/**
	 * 
	 * @param require
	 * @param period               期間
	 * @param employmentCode       雇用コード
	 * @param IgnoreFlagDuringLock ロック中無視フラグ
	 * @param achievementAtr       ロック確認単位
	 * @return 期間＜List＞
	 */
	public static List<DatePeriod> get(GetPeriodCanProcesse.Require require, DatePeriod period, String employmentCode,
			IgnoreFlagDuringLock ignoreFlagDuringLock, AchievementAtr achievementAtr) {
		List<DatePeriod> listPeriod = new ArrayList<>();
		// ロック中無視フラグを確認する
		if (ignoreFlagDuringLock == IgnoreFlagDuringLock.CAN_CAL_LOCK) {
			listPeriod.add(period);
			return listPeriod;
		}
		// Requireで締めIDを取得する
		Optional<ClosureEmployment> optClosureEmployment = require.findByEmploymentCD(employmentCode);
		if (!optClosureEmployment.isPresent()) {
			return listPeriod;
		}
		// Requireで「当月の実績ロック」を取得する
		Optional<ActualLock> optActualLock = require.findById(optClosureEmployment.get().getClosureId());
		if (!optActualLock.isPresent()) {
			listPeriod.add(period);
			return listPeriod;
		}
		// ロックされていない期間を求める
		listPeriod.addAll(optActualLock.get().askForUnlockedPeriod(require, period, achievementAtr));
		return listPeriod;

	}
}
