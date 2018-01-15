package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

/**
 * 休日から休日への0時跨ぎ設定
 * @author keisuke_hoshina
 *
 */
@Getter
public class OverDayEndSetOfHolidayHoliday {
	private HolidayWorkFrameNo holidayWorkFrameNo;
	private HolidayWorkFrameNo transferFrameNoOfStatutoryHoliday;
	private HolidayWorkFrameNo transferFrameNoOfExcessHoliday;
	private HolidayWorkFrameNo transferFrameNoOfExcessSpecialHoliday;
	
	/**
	 * 受け取った休日区分を基に枠Noを取得
	 * @param holidayAtr
	 * @return 枠No
	 */
	public HolidayWorkFrameNo getFrameNoByHolidayAtr(HolidayAtr holidayAtr) {
		switch(holidayAtr) {
		case STATUTORY_HOLIDAYS:
			return transferFrameNoOfStatutoryHoliday;
		case PUBLIC_HOLIDAY:
			return transferFrameNoOfExcessSpecialHoliday;
		case NON_STATUTORY_HOLIDAYS:
			return transferFrameNoOfExcessHoliday;
		default:
			throw new RuntimeException("unknown holidayAtr:" + holidayAtr);
		}
	}
}
