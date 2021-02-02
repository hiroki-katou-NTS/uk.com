package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOver;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOver;

/**
 * 休暇上限超過消滅数
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
public class LeaveOverNumber {

	/**
	 * 日数
	 */
	public DayNumberOver numberOverDays;

	/**
	 * 時間
	 */
	public Optional<TimeOver> timeOver;

	public LeaveOverNumber(Double days, Integer minutes) {
		this.numberOverDays = new DayNumberOver(days == null? 0.0d: days.doubleValue());
		this.timeOver = minutes != null ? Optional.of(new TimeOver(minutes)) : Optional.empty();
	}

	public static LeaveOverNumber createFromJavaType(Double days, Integer minutes) {
		return new LeaveOverNumber(days, minutes);
	}

	/**
	 * ファクトリー
	 * @param numberOverDays 日数
	 * @param timeOver　時間
	 * @return LeaveUsedNumber 休暇上限超過消滅数
	*/
	public static LeaveOverNumber of(
			DayNumberOver numberOverDays,
			Optional<TimeOver> timeOver) {

		LeaveOverNumber domain = new LeaveOverNumber(numberOverDays, timeOver);
		domain.numberOverDays = numberOverDays;
		domain.timeOver = timeOver;
		return domain;
	}

	@Override
	public LeaveOverNumber clone() {

		LeaveOverNumber cloned;

		cloned = new LeaveOverNumber(
				numberOverDays,
				timeOver.map(c -> new TimeOver(c.v())));

		return cloned;
	}
}
