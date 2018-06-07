package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeAnnualLeaveMax {

	/**
	 * 上限時間
	 */
	private MaxMinutes maxMinutes;

	/**
	 * 使用時間
	 */
	private UsedMinutes usedMinutes;

	/**
	 * 残時間
	 */
	private RemainingMinutes remainingMinutes;

	public void updateMaxMinutes(MaxMinutes maxMinutes) {
		this.maxMinutes = maxMinutes;
		updateRemainingMinutes();
	}

	public void updateUsedMinutes(UsedMinutes usedMinutes) {
		this.usedMinutes = usedMinutes;
		updateRemainingMinutes();
	}

	public void update(MaxMinutes maxMinutes, UsedMinutes usedMinutes) {
		this.maxMinutes = maxMinutes;
		this.usedMinutes = usedMinutes;
		updateRemainingMinutes();
	}

	private void updateRemainingMinutes() {
		this.remainingMinutes = new RemainingMinutes(this.maxMinutes.v() - this.usedMinutes.v());
	}

}
