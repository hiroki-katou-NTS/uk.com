package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の月の計算 */
public class MonthlyCalculationDto implements ItemConst {

	/** 36協定時間: 月別実績の36協定時間 */
	@AttendanceItemLayout(jpPropertyName = AGREEMENT, layout = LAYOUT_A)
	private AgreementTimeOfMonthlyDto agreementTime;

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
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = TOTAL_LABOR, layout = LAYOUT_F)
	private Integer totalWorkingTime;

	/** 法定労働時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = WITHIN_STATUTORY, layout = LAYOUT_G)
	private Integer statutoryWorkingTime;

	public MonthlyCalculation toDomain() {
		return MonthlyCalculation.of(actualWorkingTime == null ? null : actualWorkingTime.toDomain(),
									flexTime == null ? null : flexTime.toDomain(),
									statutoryWorkingTime == null ? null : new AttendanceTimeMonth(statutoryWorkingTime),
									aggregateTime == null ? null : aggregateTime.toDomain(),
									totalWorkingTime == null ? null : new AttendanceTimeMonth(totalWorkingTime),
									totalTimeSpentAtWork == null ? null : totalTimeSpentAtWork.toDomain(),
									agreementTime == null ? null : agreementTime.toDomain());
	}
	
	public static MonthlyCalculationDto from(MonthlyCalculation domain) {
		MonthlyCalculationDto dto = new MonthlyCalculationDto();
		if(domain != null) {
			dto.setAgreementTime(AgreementTimeOfMonthlyDto.from(domain.getAgreementTime()));
			dto.setFlexTime(FlexTimeOfMonthlyDto.from(domain.getFlexTime()));
			dto.setActualWorkingTime(RegularAndIrregularTimeOfMonthlyDto.from(domain.getActualWorkingTime()));
			dto.setAggregateTime(AggregateTotalWorkingTimeDto.from(domain.getAggregateTime()));
			dto.setTotalTimeSpentAtWork(AggregateTotalTimeSpentAtWorkDto.from(domain.getTotalTimeSpentAtWork()));
			dto.setTotalWorkingTime(domain.getTotalWorkingTime() == null ? null : domain.getTotalWorkingTime().valueAsMinutes());
			dto.setStatutoryWorkingTime(domain.getStatutoryWorkingTime() == null ? null : domain.getStatutoryWorkingTime().valueAsMinutes());
		}
		return dto;
	}

}
