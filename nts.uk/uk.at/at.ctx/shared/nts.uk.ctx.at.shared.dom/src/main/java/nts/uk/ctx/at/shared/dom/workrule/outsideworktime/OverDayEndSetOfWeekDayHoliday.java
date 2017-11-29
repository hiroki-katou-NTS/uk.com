package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

/**
 * 平日から休日の0時跨ぎ設定
 * @author keisuke_hoshina
 *
 */
@Getter
public class OverDayEndSetOfWeekDayHoliday {
	/*変更前の残業枠No*/
	private OverTimeFrameNo overWorkFrameNo;
	private HolidayWorkFrameNo  transferFrameNoOfStatutoryHoliday;
	private HolidayWorkFrameNo  transferFrameNoOfExcessHoliday;
	private HolidayWorkFrameNo  transferFrameNoOfExcessSpecialHoliday;
	
	
	/**
	 * 休日区分に応じた休出枠を返す
	 * @return
	 */
	public HolidayWorkFrameNo getHolidayWorkNo(HolidayAtr holidayAtr) {
		switch(holidayAtr) {
		case STATUTORY_HOLIDAYS:
			return transferFrameNoOfStatutoryHoliday;
		case PUBLIC_HOLIDAY:
			return transferFrameNoOfExcessSpecialHoliday;
		case NON_STATUTORY_HOLIDAYS:
			return transferFrameNoOfExcessHoliday;
			default :
				throw new RuntimeException("unknown holidayAtr");
		}
	}
}
