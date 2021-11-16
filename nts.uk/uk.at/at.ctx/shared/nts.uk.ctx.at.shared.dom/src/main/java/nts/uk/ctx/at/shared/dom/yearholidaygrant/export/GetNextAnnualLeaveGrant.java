package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;

/**
 * 次回年休付与を取得する
 * @author shuichu_ishida
 */
public class GetNextAnnualLeaveGrant {

	/**
	 * 次回年休付与を取得する
	 * @param companyId 会社ID
	 * @param grantTableCode 年休付与テーブル設定コード
	 * @param entryDate 入社年月日
	 * @param criteriaDate 年休付与基準日
	 * @param period 期間
	 * @param simultaneousGrantDateOpt 一斉付与日
	 * @param isSingleDay 単一日フラグ
	 * @return 次回年休付与リスト
	 */
	public static List<NextAnnualLeaveGrant> algorithm(RequireM1 require, CacheCarrier cacheCarrier,
			String companyId, String employeeId, String grantTableCode, GeneralDate entryDate, GeneralDate criteriaDate,
			DatePeriod period, boolean isSingleDay) {
		return algorithm(require, cacheCarrier, companyId, employeeId, grantTableCode, entryDate, criteriaDate,
				period, isSingleDay, Optional.empty(), Optional.empty());
	}

	/**
	 * 次回年休付与を取得する
	 * @param companyId 会社ID
	 * @param grantTableCode 年休付与テーブル設定コード
	 * @param entryDate 入社年月日
	 * @param criteriaDate 年休付与基準日
	 * @param period 期間
	 * @param simultaneousGrantDateOpt 一斉付与日
	 * @param isSingleDay 単一日フラグ
	 * @param grantHdTblSet 年休付与テーブル設定
	 * @param lengthServiceTbls 勤続年数テーブルリスト
	 * @param closureStartDate 締め開始日
	 * @return 次回年休付与リスト
	 */
	public static List<NextAnnualLeaveGrant> algorithm(RequireM1 require, CacheCarrier cacheCarrier,
			String companyId, String employeeId,
			String grantTableCode, GeneralDate entryDate, GeneralDate criteriaDate, DatePeriod period,
			boolean isSingleDay, Optional<GrantHdTblSet> grantHdTblSet, Optional<List<LengthServiceTbl>> lengthServiceTbls) {

		return algorithm(require, cacheCarrier, companyId, employeeId, grantTableCode, entryDate, criteriaDate, period,
						isSingleDay,grantHdTblSet, lengthServiceTbls, Optional.empty());
	}

	public static List<NextAnnualLeaveGrant> algorithm(
		RequireM1 require, CacheCarrier cacheCarrier,
		String companyId, String employeeId,
		String grantTableCode, GeneralDate entryDate, GeneralDate criteriaDate, DatePeriod period,
		boolean isSingleDay, Optional<GrantHdTblSet> grantHdTblSet, Optional<List<LengthServiceTbl>> lengthServiceTbls,
		Optional<GeneralDate> closureStartDate) {

		return GetNextAnnualLeaveGrantProc.algorithm(require, cacheCarrier,
														companyId, employeeId, grantTableCode, entryDate, criteriaDate, period,
														isSingleDay,grantHdTblSet, lengthServiceTbls, closureStartDate);
	}

	public static interface RequireM1 extends GetNextAnnualLeaveGrantProc.RequireM1{

	}
}
