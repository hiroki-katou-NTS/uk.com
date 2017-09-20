/**
 * 9:19:40 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.RoundingAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UnitAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPayTime;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.TimeItemId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.AttendanceClock;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author hungnm
 *
 */
@Getter
public class BonusPayTimesheet extends DomainObject {

	private int timeSheetId;

	private UseAtr useAtr;

	private String timeItemId;

	private AttendanceClock startTime;

	private AttendanceClock endTime;

	private UnitAtr roundingTimeAtr;

	private RoundingAtr roundingAtr;

	protected BonusPayTimesheet(int timeSheetId, UseAtr useAtr, String timeItemId,
			AttendanceClock startTime, AttendanceClock endTime, UnitAtr roundingTimeAtr,
			RoundingAtr roundingAtr) {
		super();
		this.timeSheetId = timeSheetId;
		this.useAtr = useAtr;
		this.timeItemId = timeItemId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roundingTimeAtr = roundingTimeAtr;
		this.roundingAtr = roundingAtr;
	}

	protected BonusPayTimesheet() {
		super();
	}

	public static BonusPayTimesheet createFromJavaType(int timeSheetId, int useAtr, String timeItemId, Long startTime,
			Long endTime, int roundingTimeAtr, int roundingAtr) {
		return new BonusPayTimesheet(timeSheetId, EnumAdaptor.valueOf(useAtr, UseAtr.class), timeItemId,
				new AttendanceClock(startTime), new AttendanceClock(endTime),
				EnumAdaptor.valueOf(roundingTimeAtr, UnitAtr.class),
				EnumAdaptor.valueOf(roundingAtr, RoundingAtr.class));
	}
	
	/**
	 * 開始と終了時刻を入れ替え作り直す
	 * @return 加給時間帯クラス
	 */
	public BonusPayTimesheet reCreateCalcRange(TimeSpanForCalc newRange) {
		return new BonusPayTimesheet(this.timeSheetId,this.useAtr,this.getTimeItemId(),newRange.getStart(),newRange.getEnd(),this.getRoundingTimeAtr(),this.getRoundingAtr());
	}
	
}
