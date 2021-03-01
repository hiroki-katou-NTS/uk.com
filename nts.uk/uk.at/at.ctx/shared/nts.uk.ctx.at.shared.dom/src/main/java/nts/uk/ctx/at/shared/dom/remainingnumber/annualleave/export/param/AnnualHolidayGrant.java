package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedTime;
/**
 * 年休付与
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnnualHolidayGrant {
	/**付与日	 */
	private GeneralDate ymd;
	/**年休付与数	 */
	private double grantDays;
	/**年休使用数	 */
	private double useDays;
	/**期限日	 */
	private GeneralDate deadline;
	/**年休残数	 */
	private double remainDays;
	/**時間付与数 */
	private LeaveGrantTime grantMinutes;
	/**時間使用数 */
	private LeaveUsedTime usedMinutes;
	/**時間残数 */
	private LeaveRemainingTime remainMinutes;
	
	public static AnnualHolidayGrant fromData(AnnualLeaveGrantRemainingData data) {
		return new AnnualHolidayGrant(
				data.getGrantDate(),
				data.getDetails().getGrantNumber().getDays().v(),
				data.getDetails().getUsedNumber().getDays().v(),
				data.getDeadline(),
				data.getDetails().getRemainingNumber().getDays().v(),
				data.getDetails().getGrantNumber().getMinutes().orElse(new LeaveGrantTime(0)),
				data.getDetails().getUsedNumber().getMinutes().orElse(new LeaveUsedTime(0)),
				data.getDetails().getRemainingNumber().getMinutes().orElse(new LeaveRemainingTime(0)));
	}
}
