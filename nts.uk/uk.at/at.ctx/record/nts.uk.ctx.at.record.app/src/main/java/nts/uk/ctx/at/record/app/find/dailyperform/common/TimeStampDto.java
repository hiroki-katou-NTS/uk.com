package nts.uk.ctx.at.record.app.find.dailyperform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
/** 勤怠打刻 */
@AllArgsConstructor
@NoArgsConstructor
public class TimeStampDto implements ItemConst {

	/** 時刻 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = CLOCK)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer timesOfDay;

	/** 丸め後の時刻 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = ROUNDING)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer afterRoundingTimesOfDay;

	/** 場所コード */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = PLACE)
	@AttendanceItemValue
	private String placeCode;
	
	private int stampSourceInfo;
	
	public static TimeStampDto createTimeStamp(WorkStamp c) {
		return c == null || c.getTimeWithDay() == null ? null : new TimeStampDto(c.getTimeWithDay().valueAsMinutes(),
												c.getAfterRoundingTime().valueAsMinutes(),
												!c.getLocationCode().isPresent() ? null : c.getLocationCode().get().v(),
												c.getStampSourceInfo().value);
	}
	
	@Override
	public TimeStampDto clone() {
		return new TimeStampDto(timesOfDay, afterRoundingTimesOfDay, placeCode, stampSourceInfo);
	}
	
	public static WorkStamp toDomain(TimeStampDto c) {
		return c == null || c.getTimesOfDay() == null ? null : new WorkStamp(
				c.getAfterRoundingTimesOfDay() == null ? TimeWithDayAttr.THE_PRESENT_DAY_0000 : new TimeWithDayAttr(c.getAfterRoundingTimesOfDay()),
				new TimeWithDayAttr(c.getTimesOfDay()),
				c.getPlaceCode() == null ? null : new WorkLocationCD(c.getPlaceCode()),
				c.stampInfo());
	}
	
	public TimeChangeMeans stampInfo(){
		switch (stampSourceInfo) {
		case 0:
			return TimeChangeMeans.TIME_RECORDER;
		case 1:
			return TimeChangeMeans.STAMP_APPLICATION;
		case 2:
			return TimeChangeMeans.STAMP_APPLICATION_NR;
		case 3:
			return TimeChangeMeans.GO_STRAIGHT;
		case 4:
			return TimeChangeMeans.GO_STRAIGHT_APPLICATION;
		case 5:
			return TimeChangeMeans.GO_STRAIGHT_APPLICATION_BUTTON;
		case 6:
			return TimeChangeMeans.HAND_CORRECTION_BY_MYSELF;
		case 7:
			return TimeChangeMeans.HAND_CORRECTION_BY_ANOTHER;
		case 8:
			return TimeChangeMeans.STAMP_AUTO_SET_PERSONAL_INFO;
		case 9:
			return TimeChangeMeans.CORRECTION_RECORD_SET;
		case 10:
			return TimeChangeMeans.TIME_RECORDER_ID_INPUT;
		case 11:
			return TimeChangeMeans.WEB_STAMP_INPUT;
		case 12:
			return TimeChangeMeans.TIME_RECORDER_MAGNET_CARD;
		case 13:
			return TimeChangeMeans.TIME_RECORDER_Ic_CARD;
		case 14:
			return TimeChangeMeans.TIME_RECORDER_FINGER_STAMP;
		case 15:
			return TimeChangeMeans.MOBILE_STAMP;
		case 16:
			return TimeChangeMeans.MOBILE_STAMP_OUTSIDE;
		case 17:
			return TimeChangeMeans.STAMP_LEAKAGE_CORRECTION;
		case 18:
		default:
			return TimeChangeMeans.SPR;
		}
	}
}
