package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の残業時間 */
public class OverTimeOfMonthlyDto implements ItemConst {

	/** 残業合計時間 */
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_A)
	private TimeMonthWithCalculationDto totalOverTime;

	/** 事前残業時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private Integer beforeOverTime;

	/** 振替残業合計時間 */
	@AttendanceItemLayout(jpPropertyName = TRANSFER + TOTAL, layout = LAYOUT_C)
	private TimeMonthWithCalculationDto totalTransferOverTime;

	/** 集計残業時間 */
	@AttendanceItemLayout(jpPropertyName = AGGREGATE, layout = LAYOUT_D, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<AggregateOverTimeDto> aggregateOverTimeMap;

	public OverTimeOfMonthly toDomain() {
		return OverTimeOfMonthly.of(totalOverTime == null ? null : totalOverTime.toDomain(),
									beforeOverTime == null ? null : new AttendanceTimeMonth(beforeOverTime),
									totalTransferOverTime == null ? null : totalTransferOverTime.toDomain(),
									ConvertHelper.mapTo(aggregateOverTimeMap, c -> c.toDomain()));
	}
	
	public static OverTimeOfMonthlyDto from(OverTimeOfMonthly domain) {
		OverTimeOfMonthlyDto dto = new OverTimeOfMonthlyDto();
		if(domain != null) {
			dto.setAggregateOverTimeMap(ConvertHelper.mapTo(domain.getAggregateOverTimeMap(), c -> AggregateOverTimeDto.from(c.getValue())));
			dto.setBeforeOverTime(domain.getBeforeOverTime() == null ? null : domain.getBeforeOverTime().valueAsMinutes());
			dto.setTotalOverTime(TimeMonthWithCalculationDto.from(domain.getTotalOverTime()));
			dto.setTotalTransferOverTime(TimeMonthWithCalculationDto.from(domain.getTotalTransferOverTime()));
		}
		return dto;
	}
}
