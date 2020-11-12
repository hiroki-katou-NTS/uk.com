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

	@Override
	public LeaveOverNumber clone() {

		LeaveOverNumber cloned;
		try {
			cloned = new LeaveOverNumber(
					numberOverDays,
					timeOver.map(c -> new TimeOver(c.v())));
		}
		catch (Exception e){
			throw new RuntimeException("LeaveGrantRemainingData clone error.");
		}
		return cloned;
	}
}
