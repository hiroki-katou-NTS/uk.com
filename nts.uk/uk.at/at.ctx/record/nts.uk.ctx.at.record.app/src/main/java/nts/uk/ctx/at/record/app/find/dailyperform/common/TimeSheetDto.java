package nts.uk.ctx.at.record.app.find.dailyperform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/**　時間帯　*/
@AllArgsConstructor
@NoArgsConstructor
public class TimeSheetDto {
	
	private Integer timeSheetNo;

	@AttendanceItemLayout(layout="A", jpPropertyName="開始時間")
	private TimeStampDto start;

	@AttendanceItemLayout(layout="B", jpPropertyName="終了時間")
	private TimeStampDto end;
	
	private int breakTime;
}
