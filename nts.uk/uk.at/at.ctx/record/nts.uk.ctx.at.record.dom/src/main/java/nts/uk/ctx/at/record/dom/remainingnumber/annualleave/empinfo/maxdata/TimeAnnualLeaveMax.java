package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata;

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

}
