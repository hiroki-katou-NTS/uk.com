package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;

/**
 * 締め開始日と年休付与テーブルから次回年休付与を計算する
 * @author shuichu_ishida
 */
public interface CalcNextAnnLeaGrantInfo {

	/**
	 * 締め開始日と年休付与テーブルから次回年休付与を計算する
	 * @param companyId 会社ID
	 * @param closureStart 締め開始日
	 * @param entryDate 入社年月日
	 * @param criteriaDate 年休付与基準日
	 * @param grantTableCode 年休付与テーブル設定コード
	 * @param contractTime 契約時間
	 * @return 次回年休付与
	 */
	Optional<NextAnnualLeaveGrant> algorithm(String companyId, GeneralDate closureStart, GeneralDate entryDate,
			GeneralDate criteriaDate, String grantTableCode, Optional<LimitedTimeHdTime> contractTime);
}
