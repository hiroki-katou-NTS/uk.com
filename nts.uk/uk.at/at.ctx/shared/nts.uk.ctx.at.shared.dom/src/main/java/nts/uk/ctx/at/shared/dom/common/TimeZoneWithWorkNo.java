package nts.uk.ctx.at.shared.dom.common;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
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

	public TimeZoneWithWorkNo(int workNo, Integer startTime, Integer endTime) {
		this.workNo = new WorkNo(workNo);
		this.timeZone = new TimeZone(startTime, endTime);
	}
	
	public void validate() {
		if(this.timeZone.getStartTime() == null || this.timeZone.getStartTime() == null 
				|| this.timeZone.getStartTime().greaterThan(this.timeZone.getEndTime())) {
			throw new BusinessException("Msg_966");
		}
		//開始時刻 >= 5:00 (#Msg_307#)
		//終了時刻 <= 29:00(#Msg_307#)
		if(this.timeZone.getStartTime().lessThan(300) || this.timeZone.getEndTime().greaterThan(1740)) {
			throw new BusinessException("Msg_307");
		}
	}
}
