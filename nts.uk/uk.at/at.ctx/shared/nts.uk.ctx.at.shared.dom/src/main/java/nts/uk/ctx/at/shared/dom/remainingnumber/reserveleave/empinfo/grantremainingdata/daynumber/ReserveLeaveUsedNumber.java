package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveOverNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedTime;

@Getter
public class ReserveLeaveUsedNumber extends LeaveUsedNumber {

	/**
	 * ファクトリー
	 * @param days 日数
	 * @param minutes　時間
	 * @param stowageDays 積み崩し日数
	 * @param leaveOverLimitNumber 上限超過消滅日数
	 * @return LeaveUsedNumber 休暇使用数
	*/
	public static ReserveLeaveUsedNumber of(
			LeaveUsedDayNumber days,
			Optional<LeaveUsedTime> minutes,
			Optional<LeaveUsedDayNumber> stowageDays,
			Optional<LeaveOverNumber> leaveOverLimitNumber) {

		ReserveLeaveUsedNumber domain = new ReserveLeaveUsedNumber();
		domain.days = days;
		domain.minutes = minutes;
		domain.stowageDays = stowageDays;
		domain.leaveOverLimitNumber = leaveOverLimitNumber;
		return domain;
	}

}
