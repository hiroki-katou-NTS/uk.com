package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の残業時間 */
public class OverTimeOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 残業合計時間 */
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_A)
	private TimeMonthWithCalculationDto totalOverTime;

	/** 事前残業時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private int beforeOverTime;

	/** 振替残業合計時間 */
	@AttendanceItemLayout(jpPropertyName = TRANSFER + TOTAL, layout = LAYOUT_C)
	private TimeMonthWithCalculationDto totalTransferOverTime;

	/** 集計残業時間 */
	@AttendanceItemLayout(jpPropertyName = AGGREGATE, layout = LAYOUT_D, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<AggregateOverTimeDto> aggregateOverTimeMap;

	public OverTimeOfMonthly toDomain() {
		return OverTimeOfMonthly.of(totalOverTime == null ? new TimeMonthWithCalculation() : totalOverTime.toDomain(),
									new AttendanceTimeMonth(beforeOverTime),
									totalTransferOverTime == null ? new TimeMonthWithCalculation() : totalTransferOverTime.toDomain(),
									ConvertHelper.mapTo(aggregateOverTimeMap, c -> c.toDomain()));
	}
	
	public static OverTimeOfMonthlyDto from(OverTimeOfMonthly domain) {
		OverTimeOfMonthlyDto dto = new OverTimeOfMonthlyDto();
		if(domain != null) {
			dto.setAggregateOverTimeMap(ConvertHelper.mapTo(domain.getAggregateOverTimeMap(), c -> AggregateOverTimeDto.from(c.getValue())));
			dto.setBeforeOverTime(domain.getBeforeOverTime() == null ? 0 : domain.getBeforeOverTime().valueAsMinutes());
			dto.setTotalOverTime(TimeMonthWithCalculationDto.from(domain.getTotalOverTime()));
			dto.setTotalTransferOverTime(TimeMonthWithCalculationDto.from(domain.getTotalTransferOverTime()));
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		if (BEFORE.equals(path)) {
			return Optional.of(ItemValue.builder().value(beforeOverTime).valueType(ValueType.TIME));
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case TOTAL:
		case (TRANSFER + TOTAL):
			return new TimeMonthWithCalculationDto();
		case AGGREGATE:
			return new AggregateOverTimeDto();
		default:
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case TOTAL:
			return Optional.ofNullable(totalOverTime);
		case (TRANSFER + TOTAL):
			return Optional.ofNullable(totalTransferOverTime);
		default:
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public int size(String path) {
		if(AGGREGATE.equals(path)){
			return 10;
		}
		return AttendanceItemDataGate.super.size(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case BEFORE:
			return PropType.VALUE;
		case AGGREGATE:
			return PropType.IDX_LIST;
		default:
			return PropType.OBJECT;
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if(AGGREGATE.equals(path)){
			return (List<T>) aggregateOverTimeMap;
		}
		return AttendanceItemDataGate.super.gets(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		if (BEFORE.equals(path)) {
			beforeOverTime = value.valueOrDefault(0);
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case TOTAL:
			totalOverTime = (TimeMonthWithCalculationDto) value;
			break;
		case (TRANSFER + TOTAL):
			totalTransferOverTime = (TimeMonthWithCalculationDto) value;
			break;
		default:
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if(AGGREGATE.equals(path)){
			aggregateOverTimeMap = (List<AggregateOverTimeDto>) value;
		}
	}	
}
