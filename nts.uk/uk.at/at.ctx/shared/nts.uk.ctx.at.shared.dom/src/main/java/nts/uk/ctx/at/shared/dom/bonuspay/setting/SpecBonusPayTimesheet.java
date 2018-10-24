/**
 * 9:20:03 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.RoundingAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UnitAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.SpecBonusPayNumber;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.AttendanceClock;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author hungnm
 * 特定日加給時間帯
 *
 */
@Getter
public class SpecBonusPayTimesheet {

	private int timeSheetId;

	private UseAtr useAtr;
	//特定加給時間項目No
	private int timeItemId;

	private AttendanceClock startTime;

	private AttendanceClock endTime;

	private UnitAtr roundingTimeAtr;

	private RoundingAtr roundingAtr;
	
	private int dateCode;
	//特定日項目No
	private SpecBonusPayNumber specBonusPayNumber;

	public SpecBonusPayTimesheet(
			int timeSheetId, UseAtr useAtr, int timeItemId,
			AttendanceClock startTime, AttendanceClock endTime, UnitAtr roundingTimeAtr,
			RoundingAtr roundingAtr, int dateCode,SpecBonusPayNumber specBonusPayNumber) {
		//super(timeSheetId, useAtr, timeItemId, startTime, endTime, roundingTimeAtr, roundingAtr);
		this.timeSheetId = timeSheetId;
		this.useAtr = useAtr;
		this.timeItemId = timeItemId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roundingTimeAtr = roundingTimeAtr;
		this.roundingAtr = roundingAtr;
		this.dateCode = dateCode;
		this.specBonusPayNumber = specBonusPayNumber;
	}

	public static SpecBonusPayTimesheet createFromJavaType(int timeSheetId, int useAtr, int timeItemId,
			int startTime, int endTime, int roundingTimeAtr, int roundingAtr, int dateCode) {
		return new SpecBonusPayTimesheet(
				timeSheetId, EnumAdaptor.valueOf(useAtr, UseAtr.class),
				timeItemId, new AttendanceClock(startTime),
				new AttendanceClock(endTime), EnumAdaptor.valueOf(roundingTimeAtr, UnitAtr.class),
				EnumAdaptor.valueOf(roundingAtr, RoundingAtr.class), dateCode,new SpecBonusPayNumber(BigDecimal.ONE));
	}
	
	

	
}
