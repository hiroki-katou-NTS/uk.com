package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.commonsetting.primitive.WorkTimeNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

/**
 * 休出時間の時間帯設定
 * @author keisuke_hoshina
 *
 */
@Getter
public class HolidayWorkTimeSheetSet {
	private TimeSpanWithRounding hours;
	
	private WorkTimeNo workTimeNo;
	
	private HolidayWorkFrameNo legalHolidayFrameNo;
	private HolidayWorkFrameNo statutoryHolidayFrameNo;
	private HolidayWorkFrameNo nonStatutoryDayoffConstraintTime;
	
	private boolean isLegalHolidayConstraintTime;
	private boolean isNonStatutoryHolidayConstraintTime;
	private boolean isNonStatutoryDayoffConstraintTime;
	
	/**
	 * 休日区分に従って枠Noを取得する
	 * @return 休出枠No
	 */
	public HolidayWorkFrameNo getFrameNoByHolidayAtr(HolidayAtr holidayAtr) {
		switch(holidayAtr) {
		case STATUTORY_HOLIDAYS:
			return statutoryHolidayFrameNo;
		case PUBLIC_HOLIDAY:
			return legalHolidayFrameNo;
		case NON_STATUTORY_HOLIDAYS:
			return nonStatutoryDayoffConstraintTime;
		default:
			throw new RuntimeException("unknown holidayAtr:"+holidayAtr);
		}
	}
}
