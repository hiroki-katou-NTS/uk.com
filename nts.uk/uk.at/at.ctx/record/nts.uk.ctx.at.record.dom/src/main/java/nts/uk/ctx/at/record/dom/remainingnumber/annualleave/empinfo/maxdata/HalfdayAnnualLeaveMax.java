package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata;

import lombok.Getter;

@Getter
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

}
