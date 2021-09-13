package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;

/**
 * 締め開始日と年休付与テーブルから次回年休付与を計算する
 * @author shuichu_ishida
 */
public class CalcNextAnnLeaGrantInfo {

	/**
	 * 締め開始日と年休付与テーブルから次回年休付与を計算する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param closureStart 締め開始日
	 * @param entryDate 入社年月日
	 * @param criteriaDate 年休付与基準日
	 * @param grantTableCode 年休付与テーブル設定コード
	 * @param contractTime 契約時間
	 * @return 次回年休付与
	 */
	public static Optional<NextAnnualLeaveGrant> algorithm(RequireM1 require, CacheCarrier cacheCarrier,
			String companyId, String employeeId, GeneralDate closureStart, GeneralDate entryDate,
			GeneralDate criteriaDate, String grantTableCode, Optional<LimitedTimeHdTime> contractTime) {

		// 「入社年月日」と「締め開始日」を比較　→　次回年休付与計算開始日
		GeneralDate nextGrant = closureStart;
		if (entryDate.after(closureStart)) nextGrant = entryDate;

		// 次回年休付与計算開始日+1日
		if (nextGrant.before(GeneralDate.max())) nextGrant = nextGrant.addDays(1);

		// 次回年休付与を取得する
		val results = GetNextAnnualLeaveGrant.algorithm(require, cacheCarrier, companyId, employeeId, grantTableCode, entryDate, criteriaDate,
				new DatePeriod(nextGrant, GeneralDate.max()), true);

		// 次回年休付与を返す
		if (results.size() == 0) return Optional.empty();
		return Optional.of(results.get(0));
	}

	public static interface RequireM1 extends GetNextAnnualLeaveGrant.RequireM1 {}
}
