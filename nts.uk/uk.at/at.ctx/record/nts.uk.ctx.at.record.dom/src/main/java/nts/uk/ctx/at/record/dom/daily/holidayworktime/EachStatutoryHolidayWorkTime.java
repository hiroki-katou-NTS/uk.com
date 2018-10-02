package nts.uk.ctx.at.record.dom.daily.holidayworktime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;

/**
 * 法定区分ごとに休出時間を保持するためのクラス
 * @author keisuke_hoshina
 *
 */
@NoArgsConstructor
@Getter
public class EachStatutoryHolidayWorkTime {
	//法内
	private AttendanceTime statutory = new AttendanceTime(0);
	//法外
	private AttendanceTime excess = new AttendanceTime(0);
	//祝日
	private AttendanceTime publicholiday = new AttendanceTime(0);
	
	/**
	 * 法定区分毎の時間を保持させる
	 * @param statutoryAtr　法定区分
	 * @param time　時間
	 */
	public void addTime(StaturoryAtrOfHolidayWork statutoryAtr,AttendanceTime time) {
		switch(statutoryAtr) {
		case WithinPrescribedHolidayWork:
			statutory = statutory.addMinutes(time.valueAsMinutes());
			break;
		case ExcessOfStatutoryHolidayWork:
			excess = excess.addMinutes(time.valueAsMinutes());
			break;
		case PublicHolidayWork:
			publicholiday = publicholiday.addMinutes(time.valueAsMinutes());
			break;
		default:
			throw new RuntimeException("unknown statutoryAtr:"+statutoryAtr);
		}
	}
	
	/**
	 * 法定区分に従って時間を返す
	 * @return　休出時間
	 */
	public AttendanceTime getTimeBaseOnAtr(StaturoryAtrOfHolidayWork statutoryAtr) {
		switch(statutoryAtr) {
		case WithinPrescribedHolidayWork:
			return statutory;
		case ExcessOfStatutoryHolidayWork:
			return excess;
		case PublicHolidayWork:
			return publicholiday;
		default:
			throw new RuntimeException("unknown statutoryAtr:"+statutoryAtr);
		}
	}
	
}
