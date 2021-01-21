package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.premiumitem.PriceUnit;

@Getter
/** 日別勤怠の応援作業時間 */
public class OuenWorkTimeOfDailyAttendance implements DomainObject {

	/** 応援勤務枠No: 応援勤務枠No */
	private int workNo;
	
	/** 勤務時間: 時間帯別勤怠の時間 */
	private OuenAttendanceTimeEachTimeSheet workTime;

	/** 移動時間: 応援別勤務の移動時間 */
	private OuenMovementTimeEachTimeSheet moveTime;
	
	/** 金額: 勤怠日別金額 */
	private AttendanceAmountDaily amount;
	
	/** 単価: 単価 */
	private PriceUnit priceUnit;

	private OuenWorkTimeOfDailyAttendance(int workNo, OuenAttendanceTimeEachTimeSheet workTime,
			OuenMovementTimeEachTimeSheet moveTime, AttendanceAmountDaily amount, PriceUnit priceUnit) {
		super();
		this.workNo = workNo;
		this.workTime = workTime;
		this.moveTime = moveTime;
		this.amount = amount;
		this.priceUnit = priceUnit;
	}
	
	public static OuenWorkTimeOfDailyAttendance create(int workNo,
			OuenAttendanceTimeEachTimeSheet workTime,
			OuenMovementTimeEachTimeSheet moveTime, AttendanceAmountDaily amount, PriceUnit priceUnit) {
		
		return new OuenWorkTimeOfDailyAttendance(workNo, workTime, moveTime, amount, priceUnit);
	}
}
