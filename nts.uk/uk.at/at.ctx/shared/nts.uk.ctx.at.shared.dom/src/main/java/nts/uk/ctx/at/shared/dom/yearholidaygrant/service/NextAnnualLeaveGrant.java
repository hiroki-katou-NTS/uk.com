package nts.uk.ctx.at.shared.dom.yearholidaygrant.service;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;

/**
 * 次回年休付与
 * @author tanlv
 *
 */
public class NextAnnualLeaveGrant {
	/**
	 * 付与年月日
	 */
	public GeneralDate grantDate;
	
	/**
	 * 回数
	 */
	public int time;
	
	/**
	 * 付与日数
	 */
	public Finally<GeneralDate> grantDays;
	
	/**
	 * 半日年休上限回数
	 */
	public Optional<Integer> halfDayAnnualLeaveMaxTimes;
	
	/**
	 * 時間年休上限日数
	 */
	public Optional<Integer> timeAnnualLeaveMaxDays;
	
	/**
	 * 時間年休上限時間
	 */
	public Optional<Integer> timeAnnualLeaveMaxTime;
}
