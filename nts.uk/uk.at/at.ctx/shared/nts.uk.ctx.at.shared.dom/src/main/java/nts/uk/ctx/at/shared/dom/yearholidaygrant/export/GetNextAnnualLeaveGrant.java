package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RepositoriesRequiredByRemNum;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 次回年休付与を取得する
 * @author shuichu_ishida
 */
public interface GetNextAnnualLeaveGrant {

	/**
	 * 次回年休付与を取得する
	 * @param repositoriesRequiredByRemNum 残数処理 キャッシュデータ
	 * @param companyId 会社ID
	 * @param grantTableCode 年休付与テーブル設定コード
	 * @param entryDate 入社年月日
	 * @param criteriaDate 年休付与基準日
	 * @param period 期間
	 * @param simultaneousGrantDateOpt 一斉付与日
	 * @param isSingleDay 単一日フラグ
	 * @return 次回年休付与リスト
	 */
	List<NextAnnualLeaveGrant> algorithm(
			Optional<RepositoriesRequiredByRemNum> repositoriesRequiredByRemNumOpt,
			String companyId, String grantTableCode, GeneralDate entryDate,
			GeneralDate criteriaDate, DatePeriod period, boolean isSingleDay);

	/**
	 * 次回年休付与を取得する
	 * @param repositoriesRequiredByRemNum 残数処理 キャッシュデータ
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
	List<NextAnnualLeaveGrant> algorithm(
			Optional<RepositoriesRequiredByRemNum> repositoriesRequiredByRemNumOpt, 
			String companyId, String grantTableCode, GeneralDate entryDate,
			GeneralDate criteriaDate, DatePeriod period, boolean isSingleDay,
			Optional<GrantHdTblSet> grantHdTblSet, Optional<List<LengthServiceTbl>> lengthServiceTbls,
			Optional<GeneralDate> closureStartDate);
}
