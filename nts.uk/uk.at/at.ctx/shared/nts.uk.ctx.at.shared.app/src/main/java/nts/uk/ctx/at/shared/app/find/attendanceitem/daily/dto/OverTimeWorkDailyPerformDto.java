package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の残業時間 */
@Data
public class OverTimeWorkDailyPerformDto {

	/** 所定外深夜時間: 法定外残業深夜時間 */
	@AttendanceItemLayout(layout="A")
	private ExcessOfStatutoryMidNightTimeDto excessOfStatutoryMidNightTime;
	
	/** 残業枠時間帯: 残業枠時間帯 */
	@AttendanceItemLayout(layout="B", isList=true)
	private List<OverTimeFrameTimeSheetDto> overTimeFrameTimeSheet;
	
	/** 残業枠時間: 残業枠時間 */
	@AttendanceItemLayout(layout="C", isList=true)
	private List<OverTimeFrameTimeDto> overTimeFrameTime;
	
	/** 残業拘束時間: 勤怠時間 */
	@AttendanceItemLayout(layout="D")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private Integer overTimeSpentAtWork;
	
	/** 変形法定内残業: 勤怠時間 */
	@AttendanceItemLayout(layout="E")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private Integer irregularWithinPrescribedOverTimeWork;
	
	/** フレックス時間: フレックス時間　*/
	@AttendanceItemLayout(layout="F")
	private FlexTimeDto flexTime;
}
