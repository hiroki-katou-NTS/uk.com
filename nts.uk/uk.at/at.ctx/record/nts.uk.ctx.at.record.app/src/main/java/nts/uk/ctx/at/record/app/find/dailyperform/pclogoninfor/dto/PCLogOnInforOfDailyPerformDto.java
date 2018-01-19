package nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ConvertibleAttendanceItem;

@AttendanceItemRoot(rootName="日別実績のPCログオン情報")
@Data
public class PCLogOnInforOfDailyPerformDto implements ConvertibleAttendanceItem {

	private String employeeId;
	
	private GeneralDate ymd;
	
	//TODO: set list max value
//	@AttendanceItemLayout(layout = "A", jpPropertyName = "ログオン・オフ時刻", isList = true, listMaxLength = ?)
	private List<TimeSheetDto> logonTime;
}
