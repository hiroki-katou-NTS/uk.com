package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/** 60H超休換算時間 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SuperHD60HConTime {

	/** 付与時間: 勤怠月間時間 */
	private AttendanceTimeMonth grantTime;
	
	/** 精算時間: 勤怠月間時間 */
	private AttendanceTimeMonth payoffTime;
	
	/** 換算時間: 勤怠月間時間 */
	private AttendanceTimeMonth conversionTime;
	
	public SuperHD60HConTime() {
		this.payoffTime = new AttendanceTimeMonth(0);
		this.grantTime = new AttendanceTimeMonth(0);
		this.conversionTime = new AttendanceTimeMonth(0);
	}
	
	public static SuperHD60HConTime of(AttendanceTimeMonth grantTime, 
			AttendanceTimeMonth calcTime, AttendanceTimeMonth transferTime) {
		
		return new SuperHD60HConTime(grantTime, calcTime, transferTime);
	}
	
	public void sum(SuperHD60HConTime target) {

		this.grantTime = this.grantTime.addMinutes(target.getGrantTime().valueAsMinutes());
		this.payoffTime = this.payoffTime.addMinutes(target.getPayoffTime().valueAsMinutes());
		this.conversionTime = this.conversionTime.addMinutes(target.getConversionTime().valueAsMinutes());
	}
}
