package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 次回年休付与を取得する
 * @author shuichu_ishida
 */
public interface GetNextAnnualLeaveGrant {

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
	List<NextAnnualLeaveGrant> algorithm(String companyId, String grantTableCode, GeneralDate entryDate,
			GeneralDate criteriaDate, DatePeriod period, boolean isSingleDay);
}
