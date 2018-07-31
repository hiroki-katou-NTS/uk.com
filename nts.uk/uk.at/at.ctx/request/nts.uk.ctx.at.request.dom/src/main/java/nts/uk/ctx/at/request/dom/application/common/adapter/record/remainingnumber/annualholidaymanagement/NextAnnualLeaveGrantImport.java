package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement;

import java.util.Optional;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author sonnlb
 *
 */
@Value
public class NextAnnualLeaveGrantImport {
	/** 付与年月日 */
	public GeneralDate grantDate;
	/** 付与日数 */
	public Double grantDays;
	/** 回数 */
	public Integer times;
	/** 時間年休上限日数 */
	public Optional<Integer>  timeAnnualLeaveMaxDays;
	/** 時間年休上限時間 */
	public Optional<Integer> timeAnnualLeaveMaxTime;
	/** 半日年休上限回数 */
	public Optional<Integer> halfDayAnnualLeaveMaxTimes;
}
