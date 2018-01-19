package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workingtime.StayingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の滞在時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StayingTimeDto {

	/** 滞在時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "滞在時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer stayingTime;

	/** 出勤前時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "出勤前時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer beforeWoringTime;

	/** 退勤後時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "退勤後時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer afterLeaveTime;

	/** PCログオン前時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "PCログオン前時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer beforePCLogOnTime;

	/** PCログオフ後時間 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "PCログオフ後時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer afterPCLogOffTime;

	public static StayingTimeDto fromStayingTime(StayingTimeOfDaily domain) {
		return domain == null ? null : new StayingTimeDto(
				domain.getStayingTime() == null ? null : domain.getStayingTime().valueAsMinutes(),
				domain.getBeforeWoringTime() == null ? null : domain.getBeforeWoringTime().valueAsMinutes(), 
				domain.getAfterLeaveTime() == null ? null : domain.getAfterLeaveTime().valueAsMinutes(),
				domain.getBeforePCLogOnTime() == null ? null : domain.getBeforePCLogOnTime().valueAsMinutes(), 
				domain.getAfterPCLogOffTime() == null ? null : domain.getAfterPCLogOffTime().valueAsMinutes());
	}

	public StayingTimeOfDaily toDomain() {
		return new StayingTimeOfDaily(
					afterPCLogOffTime == null ? null : new AttendanceTime(afterPCLogOffTime), 
					beforePCLogOnTime == null ? null : new AttendanceTime(beforePCLogOnTime),
					beforeWoringTime == null ? null : new AttendanceTime(beforeWoringTime), 
					stayingTime == null ? null : new AttendanceTime(stayingTime),
					afterLeaveTime == null ? null : new AttendanceTime(afterLeaveTime));
	}
}
