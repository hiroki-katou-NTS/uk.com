package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;

/**
 * 次回年休付与情報を取得する
 * @author shuichu_ishida
 */
public interface GetNextAnnLeaGrantInfo {

	/**
	 * 次回年休付与情報を取得する
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
