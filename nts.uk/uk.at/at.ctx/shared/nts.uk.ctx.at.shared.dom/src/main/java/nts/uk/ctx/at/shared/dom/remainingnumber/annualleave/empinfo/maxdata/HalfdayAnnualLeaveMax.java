package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HalfdayAnnualLeaveMax {
	
	/**
	 * 上限回数
	 */
	private MaxTimes maxTimes;
	
	/**
	 * 使用回数
	 */
	private UsedTimes usedTimes;
	
	/**
	 * 残回数
	 */
	private RemainingTimes remainingTimes;
	
	public void updateMaxTimes(MaxTimes maxTimes) {
		this.maxTimes = maxTimes;
		updateRemainingTimes();
	}
	
	public void updateUsedTimes(UsedTimes usedTimes) {
		this.usedTimes = usedTimes;
		updateRemainingTimes();
	}
	
	public void update(MaxTimes maxTimes, UsedTimes usedTimes) {
		this.maxTimes = maxTimes;
		this.usedTimes = usedTimes;
		updateRemainingTimes();
	}
	
	private void updateRemainingTimes() {
		this.remainingTimes = new RemainingTimes(this.maxTimes.v() - this.usedTimes.v());
	}

}
