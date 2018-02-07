package nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.LogOnInfo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

@AttendanceItemRoot(rootName = "日別実績のPCログオン情報")
@Data
public class PCLogOnInforOfDailyPerformDto implements ConvertibleAttendanceItem {

	private String employeeId;

	private GeneralDate ymd;

	// TODO: set list max value
	// @AttendanceItemLayout(layout = "A", jpPropertyName = "ログオン・オフ時刻", isList
	// = true, listMaxLength = ?)
	private List<TimeSheetDto> logonTime;

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}

	@Override
	public Object toDomain() {
		return new PCLogOnInfoOfDaily(employeeId, ymd,
					logonTime == null ? new ArrayList<>() : ConvertHelper.mapTo(logonTime, 
							c -> new LogOnInfo(new WorkNo(c.getTimeSheetNo()),
								toWorkStamp(c.getEnd()), toWorkStamp(c.getStart()))));
	}

	private WorkStamp toWorkStamp(TimeStampDto time){
		return time == null ? null : new WorkStamp(
				time.getAfterRoundingTimesOfDay() == null ? null : new TimeWithDayAttr(time.getAfterRoundingTimesOfDay()),
				time.getTimesOfDay() == null ? null : new TimeWithDayAttr(time.getTimesOfDay()), 
				time.getPlaceCode() == null ? null : new WorkLocationCD(time.getPlaceCode()),
				time.getStampSourceInfo() == null ? StampSourceInfo.HAND_CORRECTION_BY_MYSELF 
						: ConvertHelper.getEnum(time.getStampSourceInfo(), StampSourceInfo.class));
	}
}
