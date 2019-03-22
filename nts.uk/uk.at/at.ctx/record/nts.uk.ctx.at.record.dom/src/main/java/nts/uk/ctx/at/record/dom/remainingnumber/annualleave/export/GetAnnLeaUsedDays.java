package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 年休使用日数を取得
 * @author shuichi_ishida
 */
public interface GetAnnLeaUsedDays {
	
	/**
	 * 社員の前回付与日から次回付与日までの年休使用日数を取得
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param referenceAtr 参照先区分
	 * @return 年休使用合計数
	 */
	Optional<AnnualLeaveUsedDayNumber> ofGrantPeriod(String employeeId, GeneralDate criteria,
			ReferenceAtr referenceAtr);
	
	/**
	 * 指定した期間の年休使用数を取得する
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param referenceAtr 参照先区分
	 * @return 年休使用合計数
	 */
	Optional<AnnualLeaveUsedDayNumber> ofPeriod(String employeeId, DatePeriod period,
			ReferenceAtr referenceAtr);
}
