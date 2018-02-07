package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.ActualWorkTimeSheet;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.AttendanceTimeByWorkOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.WorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.primitive.ActualWorkTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.primitive.WorkFrameNo;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
@AttendanceItemRoot(rootName = "日別実績の作業別勤怠時間")
public class AttendanceTimeByWorkOfDailyDto implements ConvertibleAttendanceItem {

	//TODO: not map item id
	/** 社員ID: 社員ID */
	private String employeeId;

	/** 年月日: 年月日 */
	private GeneralDate ymd;

	/** 作業一覧: 日別実績の作業時間 */
	//TODO: set list max length
//	@AttendanceItemLayout(layout = "A", jpPropertyName = "", isList = true, listMaxLength = ?, setFieldWithIndex = "workFrameNo")
	private List<WorkTimeOfDailyDto> workTimes;
	
	public static AttendanceTimeByWorkOfDailyDto getDto(AttendanceTimeByWorkOfDaily domain) {
		AttendanceTimeByWorkOfDailyDto dto = new AttendanceTimeByWorkOfDailyDto();
		if(domain != null){
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setWorkTimes(ConvertHelper.mapTo(domain.getWorkTimes(), 
					(c) -> new WorkTimeOfDailyDto(
								c.getWorkFrameNo().v(), 
								c.getTimeSheet() == null ? null : new ActualWorkTimeSheetDto(
									getActualStamp(c.getTimeSheet().getStart()), 
									getActualStamp(c.getTimeSheet().getEnd())), 
								c.getWorkTime().valueAsMinutes())));
		}
		
		return dto;
	}

	private static WithActualTimeStampDto getActualStamp(TimeActualStamp c) {
		return c == null ? null : new WithActualTimeStampDto(
					getTimeStample(c.getStamp().orElse(null)), 
					getTimeStample(c.getActualStamp()), 
					c.getNumberOfReflectionStamp());
	}

	private static TimeStampDto getTimeStample(WorkStamp domain) {
		return domain == null ? null : new TimeStampDto(domain.getTimeWithDay().valueAsMinutes(),
						domain.getAfterRoundingTime().valueAsMinutes(), domain.getLocationCode().v(),
						domain.getStampSourceInfo().value);

	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}
	
	@Override
	public AttendanceTimeByWorkOfDaily toDomain() {
		return new AttendanceTimeByWorkOfDaily(employeeId, ymd,
					workTimes == null ? new ArrayList<>() : ConvertHelper.mapTo(workTimes,
								c -> new WorkTimeOfDaily(new WorkFrameNo(c.getWorkFrameNo()),
										new ActualWorkTimeSheet(getStamp(c.getTimeSheet().getStart()),
												getStamp(c.getTimeSheet().getEnd())),
										new ActualWorkTime(c.getWorkTime()))));
	}

	private TimeActualStamp getStamp(WithActualTimeStampDto stamp) {
		return stamp == null ? null
				: new TimeActualStamp(getWorkStamp(stamp.getActualTime()), getWorkStamp(stamp.getTime()),
						stamp.getNumberOfReflectionStamp());
	}

	private WorkStamp getWorkStamp(TimeStampDto stamp) {
		return stamp == null ? null
				: new WorkStamp(
						stamp.getAfterRoundingTimesOfDay() == null ? null
								: new TimeWithDayAttr(stamp.getAfterRoundingTimesOfDay()),
						stamp.getTimesOfDay() == null ? null : new TimeWithDayAttr(stamp.getTimesOfDay()),
						new WorkLocationCD(stamp.getPlaceCode()),
						stamp.getStampSourceInfo() == null ? StampSourceInfo.HAND_CORRECTION_BY_MYSELF : ConvertHelper.getEnum(stamp.getStampSourceInfo(), StampSourceInfo.class));
	}
}
