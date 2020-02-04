package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.SpecDateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 年休使用日数を取得
 * @author shuichi_ishida
 */
public interface GetAnnLeaUsedDaysPub {
	
	/**
	 * 社員の前回付与日から次回付与日までの年休使用日数を取得
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param referenceAtr 参照先区分
	 * @param fixedOneYear 1年固定区分
	 * @param specDateAtr 指定日区分
	 * @return 年休使用合計数
	 */
	// RequestList565
	Optional<AnnualLeaveUsedDayNumber> ofGrantPeriod(String employeeId, GeneralDate criteria,
			ReferenceAtr referenceAtr, boolean fixedOneYear, SpecDateAtr specDateAtr);
	
	/**
	 * 指定した期間の年休使用数を取得する
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param referenceAtr 参照先区分
	 * @return 年休使用合計数
	 */
	// RequestList566
	Optional<AnnualLeaveUsedDayNumber> ofPeriod(String employeeId, DatePeriod period,
			ReferenceAtr referenceAtr);
}
