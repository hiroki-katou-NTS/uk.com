/**
 * 9:19:40 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.RoundingAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UnitAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * @author hungnm
 *
 */
@Getter
public class BonusPayTimesheet {

	//sheet id
	private int timeSheetId;

	private UseAtr useAtr;

	//item no
	private int timeItemId;

	private AttendanceClock startTime;

	private AttendanceClock endTime;

	private UnitAtr roundingTimeAtr;

	private RoundingAtr roundingAtr;

	public BonusPayTimesheet(
			int timeSheetId, UseAtr useAtr, int timeItemId,
			AttendanceClock startTime, AttendanceClock endTime, UnitAtr roundingTimeAtr,
			RoundingAtr roundingAtr) {
		this.timeSheetId = timeSheetId;
		this.useAtr = useAtr;
		this.timeItemId = timeItemId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roundingTimeAtr = roundingTimeAtr;
		this.roundingAtr = roundingAtr;
	}

//	protected BonusPayTimesheet() {
//		super();
//	}

	public static BonusPayTimesheet createFromJavaType(int timeSheetId, int useAtr, int timeItemId, int startTime,
			int endTime, int roundingTimeAtr, int roundingAtr) {
		return new BonusPayTimesheet(
				timeSheetId, EnumAdaptor.valueOf(useAtr, UseAtr.class), timeItemId,
				new AttendanceClock(startTime), new AttendanceClock(endTime),
				EnumAdaptor.valueOf(roundingTimeAtr, UnitAtr.class),
				EnumAdaptor.valueOf(roundingAtr, RoundingAtr.class));
	}
	
//                           -----------------origin---------------
//	public static BonusPayTimesheet createFromJavaType(int timeSheetId, int useAtr, String timeItemId, int startTime,
//			int endTime, int roundingTimeAtr, int roundingAtr) {
//		return new BonusPayTimesheet(timeSheetId, EnumAdaptor.valueOf(useAtr, UseAtr.class), timeItemId,
//				new BonusPayTime(startTime), new BonusPayTime(endTime),
//				EnumAdaptor.valueOf(roundingTimeAtr, UnitAtr.class),
//				EnumAdaptor.valueOf(roundingAtr, RoundingAtr.class));
//	}

}
