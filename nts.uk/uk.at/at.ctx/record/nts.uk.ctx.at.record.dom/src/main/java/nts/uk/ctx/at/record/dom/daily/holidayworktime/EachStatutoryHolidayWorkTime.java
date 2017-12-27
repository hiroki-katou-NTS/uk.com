package nts.uk.ctx.at.record.dom.daily.holidayworktime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;

/**
 * 法定区分ごとに休出時間を保持するためのクラス
 * @author keisuke_hoshina
 *
 */
@NoArgsConstructor
@Getter
public class EachStatutoryHolidayWorkTime {
	private int statutory = 0;
	private int excess = 0;
	private int publicholiday = 0;
	
	/**
	 * 法定区分毎の時間を保持させる
	 * @param statutoryAtr　法定区分
	 * @param time　時間
	 */
	public void addTime(StaturoryAtrOfHolidayWork statutoryAtr,int time) {
		switch(statutoryAtr) {
		case WithinPrescribedHolidayWork:
			statutory += time;
		case ExcessOfStatutoryHolidayWork:
			excess += time;
		case PublicHolidayWork:
			publicholiday += time;
		default:
			throw new RuntimeException("unknown statutoryAtr:"+statutoryAtr);
		}
	}
	
	/**
	 * 法定区分に従って時間を返す
	 * @return　休出時間
	 */
	public int getTimeBaseOnAtr(StaturoryAtrOfHolidayWork statutoryAtr) {
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
