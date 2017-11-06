/**
 * 9:20:03 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.RoundingAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UnitAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPayTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * @author hungnm
 *
 */
@Getter
public class SpecBonusPayTimesheet extends DomainObject {

	private int timeSheetId;

	private UseAtr useAtr;

	private String timeItemId;

	private AttendanceClock startTime;

	private AttendanceClock endTime;

	private UnitAtr roundingTimeAtr;

	private RoundingAtr roundingAtr;
	
	private int dateCode;

	private SpecBonusPayTimesheet(int timeSheetId, UseAtr useAtr, String timeItemId,
			AttendanceClock startTime, AttendanceClock endTime, UnitAtr roundingTimeAtr,
			RoundingAtr roundingAtr, int dateCode) {
		//super(timeSheetId, useAtr, timeItemId, startTime, endTime, roundingTimeAtr, roundingAtr);
		super();
		this.timeSheetId = timeSheetId;
		this.useAtr = useAtr;
		this.timeItemId = timeItemId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roundingTimeAtr = roundingTimeAtr;
		this.roundingAtr = roundingAtr;
		this.dateCode = dateCode;
	}

	private SpecBonusPayTimesheet() {
		super();
	}

	public static SpecBonusPayTimesheet createFromJavaType(int timeSheetId, int useAtr, String timeItemId,
			int startTime, int endTime, int roundingTimeAtr, int roundingAtr, int dateCode) {
		return new SpecBonusPayTimesheet(timeSheetId, EnumAdaptor.valueOf(useAtr, UseAtr.class),
				timeItemId, new AttendanceClock(startTime),
				new AttendanceClock(endTime), EnumAdaptor.valueOf(roundingTimeAtr, UnitAtr.class),
				EnumAdaptor.valueOf(roundingAtr, RoundingAtr.class), dateCode);
	}
	
	
	/**
	 * 開始と終了時刻を入れ替え加給時間帯に作り直す
	 * @return
	 */
	public BonusPayTimesheet reCreate(TimeSpanForCalc newRange){
		return new BonusPayTimesheet(timeSheetId,useAtr,timeItemId,newRange.getStart(),newRange.getEnd(),roundingTimeAtr,roundingAtr);//new BonusPayTimesheet(timeSheetId,useAtr,timeItemId,startTime,endTime,roundingTimeAtr,roundingAtr);
	}

}
