package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の勤務実績時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualWorkTimeDailyPerformDto {

	/** 割増時間: 日別実績の割増時間 */
	@AttendanceItemLayout(layout = "A", isList = true, jpPropertyName = "割増時間",listMaxLength = 10)
	private List<PremiumTimeDto> premiumTimes;

	/** 拘束差異時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "拘束差異時間")
	@AttendanceItemValue(itemId = 747, type = ValueType.INTEGER)
	private Integer constraintDifferenceTime;

	/** 拘束時間: 総拘束時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "拘束時間")
	private ConstraintTimeDto constraintTime;

	/** 時差勤務時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "時差勤務時間")
	@AttendanceItemValue(itemId = 575, type = ValueType.INTEGER)
	private Integer timeDifferenceWorkingHours;

	/** 総労働時間: 日別実績の総労働時間 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "総労働時間")
	private TotalWorkingTimeDto totalWorkingTime;

	/** 乖離時間: 日別実績の乖離時間 */
	@AttendanceItemLayout(layout = "F", isList = true, jpPropertyName = "乖離時間", listMaxLength = 5)
	private List<DivergenceTimeDto> divergenceTime;

	public static ActualWorkTimeDailyPerformDto toActualWorkTime(ActualWorkingTimeOfDaily domain) {
		// List<PremiumTimeDto> premiumTimes, Integer constraintDifferenceTime,
		// ConstraintTimeDto constraintTime, Integer timeDifferenceWorkingHours,
		// TotalWorkingTimeDto totalWorkingTime, List<DivergenceTimeDto>
		// divergenceTime
		return domain == null ? null
				: new ActualWorkTimeDailyPerformDto(
						Arrays.asList(
								new PremiumTimeDto(
										domain.getPremiumTimeOfDailyPerformance().getPremitumTime() == null ? null
												: domain.getPremiumTimeOfDailyPerformance().getPremitumTime()
														.valueAsMinutes(),
										domain.getPremiumTimeOfDailyPerformance().getPremiumTimeNo())),
						domain.getConstraintDifferenceTime() == null ? null
								: domain.getConstraintDifferenceTime().valueAsMinutes(),
						new ConstraintTimeDto(
								domain.getConstraintTime().getTotalConstraintTime() == null ? null
										: domain.getConstraintTime().getTotalConstraintTime().valueAsMinutes(),
								domain.getConstraintTime().getLateNightConstraintTime() == null ? null
										: domain.getConstraintTime().getLateNightConstraintTime().valueAsMinutes()),
						domain.getTimeDifferenceWorkingHours() == null ? null
								: domain.getTimeDifferenceWorkingHours().valueAsMinutes(),
						TotalWorkingTimeDto.fromTotalWorkingTime(domain.getTotalWorkingTime()),
						domain.getDivTime().getDivergenceTime() == null ? new ArrayList<>()
								: ConvertHelper.mapTo(domain.getDivTime().getDivergenceTime(),
										d -> DivergenceTimeDto.fromDivergenceTime(d)));
	}

	public ActualWorkingTimeOfDaily toDomain() {
		return ActualWorkingTimeOfDaily.of(totalWorkingTime.toDomain(), constraintTime.getLateNightConstraintTime(),
				constraintTime.getTotalConstraintTime(), constraintDifferenceTime, timeDifferenceWorkingHours);
	}
}
