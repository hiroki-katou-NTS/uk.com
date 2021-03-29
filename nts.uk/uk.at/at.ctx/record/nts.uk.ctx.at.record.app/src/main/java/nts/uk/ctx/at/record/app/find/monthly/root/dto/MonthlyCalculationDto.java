package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の月の計算 */
public class MonthlyCalculationDto implements ItemConst, AttendanceItemDataGate {

//	/** 36協定時間: 月別実績の36協定時間 */
//	@AttendanceItemLayout(jpPropertyName = AGREEMENT, layout = LAYOUT_A)
//	private AgreementTimeOfMonthlyDto agreementTime;
//
//	/** 36協定上限時間: 月別実績の36協定上限時間 */
//	@AttendanceItemLayout(jpPropertyName = AGREEMENT + UPPER_LIMIT, layout = LAYOUT_B)
//	private AgreMaxTimeOfMonthlyDto agreMaxTime;

	/** フレックス時間: 月別実績のフレックス時間 */
	@AttendanceItemLayout(jpPropertyName = FLEX, layout = LAYOUT_B)
	private FlexTimeOfMonthlyDto flexTime;

	/** 実働時間: 月別実績の通常変形時間 */
	@AttendanceItemLayout(jpPropertyName = ACTUAL, layout = LAYOUT_C)
	private RegularAndIrregularTimeOfMonthlyDto actualWorkingTime;

	/** 集計時間: 集計総労働時間 */
	@AttendanceItemLayout(jpPropertyName = AGGREGATE, layout = LAYOUT_D)
	private AggregateTotalWorkingTimeDto aggregateTime;

	/** 総拘束時間: 期間別の総拘束時間 */
	@AttendanceItemLayout(jpPropertyName = RESTRAINT, layout = LAYOUT_E)
	private AggregateTotalTimeSpentAtWorkDto totalTimeSpentAtWork;

	/** 総労働時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TOTAL_LABOR, layout = LAYOUT_F)
	private int totalWorkingTime;

	/** 法定労働時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = WITHIN_STATUTORY, layout = LAYOUT_G)
	private int statutoryWorkingTime;

	public MonthlyCalculation toDomain() {
		return MonthlyCalculation.of(actualWorkingTime == null ? new RegularAndIrregularTimeOfMonthly() : actualWorkingTime.toDomain(),
									flexTime == null ? new FlexTimeOfMonthly() : flexTime.toDomain(),
									new AttendanceTimeMonth(statutoryWorkingTime),
									aggregateTime == null ? new AggregateTotalWorkingTime() : aggregateTime.toDomain(),
									new AttendanceTimeMonth(totalWorkingTime),
									totalTimeSpentAtWork == null ? new AggregateTotalTimeSpentAtWork() : totalTimeSpentAtWork.toDomain());
	}
	
	public static MonthlyCalculationDto from(MonthlyCalculation domain) {
		MonthlyCalculationDto dto = new MonthlyCalculationDto();
		if(domain != null) {
//			dto.setAgreementTime(AgreementTimeOfMonthlyDto.from(domain.getAgreementTime()));
			dto.setFlexTime(FlexTimeOfMonthlyDto.from(domain.getFlexTime()));
			dto.setActualWorkingTime(RegularAndIrregularTimeOfMonthlyDto.from(domain.getActualWorkingTime()));
			dto.setAggregateTime(AggregateTotalWorkingTimeDto.from(domain.getAggregateTime()));
			dto.setTotalTimeSpentAtWork(AggregateTotalTimeSpentAtWorkDto.from(domain.getTotalTimeSpentAtWork()));
			dto.setTotalWorkingTime(domain.getTotalWorkingTime() == null ? null : domain.getTotalWorkingTime().valueAsMinutes());
			dto.setStatutoryWorkingTime(domain.getStatutoryWorkingTime() == null ? null : domain.getStatutoryWorkingTime().valueAsMinutes());
//			dto.setAgreMaxTime(AgreMaxTimeOfMonthlyDto.from(domain.getAgreMaxTime()));
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case TOTAL_LABOR:
			return Optional.of(ItemValue.builder().value(totalWorkingTime).valueType(ValueType.TIME));
		case WITHIN_STATUTORY:
			return Optional.of(ItemValue.builder().value(statutoryWorkingTime).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
//		case AGREEMENT:
//			return new AgreementTimeOfMonthlyDto();
//		case (AGREEMENT + UPPER_LIMIT):
//			return new AgreMaxTimeOfMonthlyDto();
		case FLEX:
			return new FlexTimeOfMonthlyDto();
		case ACTUAL:
			return new RegularAndIrregularTimeOfMonthlyDto();
		case AGGREGATE:
			return new AggregateTotalWorkingTimeDto();
		case RESTRAINT:
			return new AggregateTotalTimeSpentAtWorkDto();
		default:
			return null;
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
//		case AGREEMENT:
//			return Optional.ofNullable(agreementTime);
//		case (AGREEMENT + UPPER_LIMIT):
//			return Optional.ofNullable(agreMaxTime);
		case FLEX:
			return Optional.ofNullable(flexTime);
		case ACTUAL:
			return Optional.ofNullable(actualWorkingTime);
		case AGGREGATE:
			return Optional.ofNullable(aggregateTime);
		case RESTRAINT:
			return Optional.ofNullable(totalTimeSpentAtWork);
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case TOTAL_LABOR:
		case WITHIN_STATUTORY:
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case TOTAL_LABOR:
			totalWorkingTime = value.valueOrDefault(0);
			break;
		case WITHIN_STATUTORY:
			statutoryWorkingTime = value.valueOrDefault(0);
			break;
		default:
			break;
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
//		case AGREEMENT:
//			agreementTime = (AgreementTimeOfMonthlyDto) value;
//			break;
//		case (AGREEMENT + UPPER_LIMIT):
//			agreMaxTime = ( AgreMaxTimeOfMonthlyDto) value;
//			break;
		case FLEX:
			flexTime = ( FlexTimeOfMonthlyDto) value;
			break;
		case ACTUAL:
			actualWorkingTime = ( RegularAndIrregularTimeOfMonthlyDto) value;
			break;
		case AGGREGATE:
			aggregateTime= ( AggregateTotalWorkingTimeDto) value;
			break;
		case RESTRAINT:
			totalTimeSpentAtWork = ( AggregateTotalTimeSpentAtWorkDto) value;
			break;
		default:
		}
	}

	
}
