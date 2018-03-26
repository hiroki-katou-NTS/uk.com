package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata;

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

}
