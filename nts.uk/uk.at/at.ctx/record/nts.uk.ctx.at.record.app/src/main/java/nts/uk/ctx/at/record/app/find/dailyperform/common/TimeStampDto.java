package nts.uk.ctx.at.record.app.find.dailyperform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
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
		return  c == null || c.getTimeDay().getTimeWithDay()  == null || c.getTimeDay().getReasonTimeChange() ==null || c.getTimeDay().getReasonTimeChange().getTimeChangeMeans() ==null  || !c.getTimeDay().getTimeWithDay().isPresent()? null : new TimeStampDto(
					c.getTimeDay().getTimeWithDay().isPresent() && c.getTimeDay().getTimeWithDay() !=null ? c.getTimeDay().getTimeWithDay().get().valueAsMinutes():null,
												c.getAfterRoundingTime() == null ? null : c.getAfterRoundingTime().valueAsMinutes(),
												!c.getLocationCode().isPresent() ? null : c.getLocationCode().get().v(),
												c.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value);
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
			return TimeChangeMeans.REAL_STAMP;
		case 1:
			return TimeChangeMeans.APPLICATION;
		case 2:
			return TimeChangeMeans.DIRECT_BOUNCE;
		case 3:
			return TimeChangeMeans.DIRECT_BOUNCE_APPLICATION;
		case 4:
			return TimeChangeMeans.HAND_CORRECTION_PERSON;
		case 5:
			return TimeChangeMeans.HAND_CORRECTION_OTHERS;
		case 6:
			return TimeChangeMeans.AUTOMATIC_SET;
		case 7:
		default:
			return TimeChangeMeans.SPR_COOPERATION;
		}
	}
}
