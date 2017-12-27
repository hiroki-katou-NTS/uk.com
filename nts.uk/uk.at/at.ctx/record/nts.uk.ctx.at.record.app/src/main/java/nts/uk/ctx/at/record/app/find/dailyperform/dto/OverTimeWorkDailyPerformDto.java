package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の残業時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeWorkDailyPerformDto {

	/** 残業枠時間: 残業枠時間 */
	@AttendanceItemLayout(layout = "A", isList = true, jpPropertyName="残業枠時間")
	private List<OverTimeFrameTimeDto> overTimeFrameTime;

	/** 残業枠時間帯: 残業枠時間帯 */
//	@AttendanceItemLayout(layout = "B", isList = true)
	private List<OverTimeFrameTimeSheetDto> overTimeFrameTimeSheet;

	/** 所定外深夜時間: 法定外残業深夜時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName="所定外深夜時間")
	private ExcessOverTimeWorkMidNightTimeDto excessOfStatutoryMidNightTime;

	/** 残業拘束時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName="残業拘束時間")
	@AttendanceItemValue(itemId =745, type = ValueType.INTEGER)
	private Integer overTimeSpentAtWork;

	/** 変形法定内残業: 勤怠時間 */
	@AttendanceItemLayout(layout = "E", jpPropertyName="変形法定内残業")
	@AttendanceItemValue(itemId = 551, type = ValueType.INTEGER)
	private Integer irregularWithinPrescribedOverTimeWork;

	/** フレックス時間: フレックス時間 */
	@AttendanceItemLayout(layout = "F",jpPropertyName="フレックス時間")
	private FlexTimeDto flexTime;
	
	public static OverTimeWorkDailyPerformDto fromOverTimeWorkDailyPerform(OverTimeOfDaily domain){
		return domain == null ? null : new OverTimeWorkDailyPerformDto(
				domain.getOverTimeWorkFrameTime().stream().map(c -> new OverTimeFrameTimeDto(
						new CalcAttachTimeDto(c.getTransferTime().getCalcTime().valueAsMinutes(), c.getTransferTime().getTime().valueAsMinutes()), 
						new CalcAttachTimeDto(c.getOverTimeWork().getCalcTime().valueAsMinutes(), c.getOverTimeWork().getTime().valueAsMinutes()), 
						c.getBeforeApplicationTime().valueAsMinutes(), c
						.getOrderTime().valueAsMinutes(), 
						c.getOverWorkFrameNo().v())).collect(Collectors.toList()), 
				domain.getOverTimeWorkFrameTimeSheet().stream().map(
						c -> new OverTimeFrameTimeSheetDto(
								new TimeSpanForCalcDto(c.getTimeSpan().getStart().valueAsMinutes(), c.getTimeSpan().getEnd().valueAsMinutes()), c.getFrameNo().v())).collect(Collectors.toList()), 
				ExcessOverTimeWorkMidNightTimeDto.fromOverTimeWorkDailyPerform(domain.getExcessOverTimeWorkMidNightTime().get()), 
				domain.getOverTimeWorkSpentAtWork().valueAsMinutes(), 
				domain.getIrregularWithinPrescribedOverTimeWork().valueAsMinutes(), 
				new FlexTimeDto(new CalcAttachTimeDto(domain.getFlexTime().getFlexTime().getCalcTime().valueAsMinutes(), 
						domain.getFlexTime().getFlexTime().getTime().valueAsMinutes()),
						domain.getFlexTime().getBeforeApplicationTime().valueAsMinutes()));
	}
}
