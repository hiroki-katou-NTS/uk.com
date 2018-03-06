package nts.uk.ctx.at.record.app.find.dailyperform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
/** 勤怠打刻(実打刻付き) */
@AllArgsConstructor
@NoArgsConstructor
public class WithActualTimeStampDto {

	@AttendanceItemLayout(layout="A", jpPropertyName="打刻")
	private TimeStampDto time;

	@AttendanceItemLayout(layout="B", jpPropertyName="実打刻")
	private TimeStampDto actualTime;
	
	/** 打刻反映回数 */
	// @AttendanceItemLayout(layout = "C")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer numberOfReflectionStamp;
	
	public static WithActualTimeStampDto toWithActualTimeStamp(TimeActualStamp stamp){
		return stamp == null ? null : new WithActualTimeStampDto(
											toTimeStamp(stamp.getStamp().orElse(null)), 
											toTimeStamp(stamp.getActualStamp()),
											stamp.getNumberOfReflectionStamp());
	}
	
	private static TimeStampDto toTimeStamp(WorkStamp stamp){
		return stamp == null ? null : new TimeStampDto(
						stamp.getTimeWithDay() == null ? null : stamp.getTimeWithDay().valueAsMinutes(), 
						stamp.getAfterRoundingTime() == null ? null : stamp.getAfterRoundingTime().valueAsMinutes(), 
						stamp.getLocationCode() == null ? null : stamp.getLocationCode().v(),
						stamp.getStampSourceInfo() == null ? null : stamp.getStampSourceInfo().value);
	}
	
	public TimeActualStamp toDomain(){
		return new TimeActualStamp(toWorkStamp(actualTime), toWorkStamp(time), numberOfReflectionStamp);
	}

	private WorkStamp toWorkStamp(TimeStampDto c) {
		return c == null ? null : new WorkStamp(
						c.getAfterRoundingTimesOfDay() == null ? null
								: new TimeWithDayAttr(c.getAfterRoundingTimesOfDay()),
						c.getTimesOfDay() == null ? null : new TimeWithDayAttr(c.getTimesOfDay()),
						c.getPlaceCode() == null ? null : new WorkLocationCD(c.getPlaceCode()),
						c.getStampSourceInfo() == null ? StampSourceInfo.HAND_CORRECTION_BY_MYSELF
								: ConvertHelper.getEnum(c.getStampSourceInfo(), StampSourceInfo.class));
	}
}
