package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workingtime.StayingTimeOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の滞在時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StayingTimeDto {

	/** 滞在時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "滞在時間")
	@AttendanceItemValue(itemId = 553, type = ValueType.INTEGER)
	private Integer stayingTime;

	/** 出勤前時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "出勤前時間")
	@AttendanceItemValue(itemId = 741, type = ValueType.INTEGER)
	private Integer beforeWoringTime;

	/** 退勤後時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "退勤後時間")
	@AttendanceItemValue(itemId = 742, type = ValueType.INTEGER)
	private Integer afterLeaveTime;

	/** PCログオン前時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "PCログオン前時間")
	@AttendanceItemValue(itemId = 743, type = ValueType.INTEGER)
	private Integer beforePCLogOnTime;

	/** PCログオフ後時間 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "PCログオフ後時間")
	@AttendanceItemValue(itemId = 744, type = ValueType.INTEGER)
	private Integer afterPCLogOffTime;
	
	public static StayingTimeDto fromStayingTime(StayingTimeOfDaily domain) {
		return domain == null ? null : new StayingTimeDto(
				domain.getStayingTime().valueAsMinutes(),
				domain.getBeforeWoringTime().valueAsMinutes(),
				domain.getAfterLeaveTime().valueAsMinutes(),
				domain.getBeforePCLogOnTime().valueAsMinutes(),
				domain.getAfterPCLogOffTime().valueAsMinutes());
	}
}
