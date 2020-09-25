package nts.uk.ctx.at.shared.dom.common;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.shr.com.time.TimeZone;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.Common.時間帯(勤務NO付き)
 * @author Doan Duy Hung
 *
 */
@Getter
public class TimeZoneWithWorkNo {
	
	/**
	 * 勤務NO
	 */
	private WorkNo workNo;
	
	/**
	 * 時間帯
	 */
	private TimeZone timeZone;

	public TimeZoneWithWorkNo(int workNo, int startTime, int endTime) {
		this.workNo = new WorkNo(workNo);
		this.timeZone = new TimeZone(startTime, endTime);
	}
}
