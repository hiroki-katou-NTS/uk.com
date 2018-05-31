package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の月の計算 */
public class MonthlyCalculationDto {

	/** 36協定時間: 月別実績の36協定時間 */
	@AttendanceItemLayout(jpPropertyName = "36協定時間", layout = "A")
	private AgreementTimeOfMonthlyDto agreementTime;

	/** フレックス時間: 月別実績のフレックス時間 */
	@AttendanceItemLayout(jpPropertyName = "フレックス時間", layout = "A")
	private FlexTimeOfMonthlyDto flexTime;

	/** 実働時間: 月別実績の通常変形時間 */
	@AttendanceItemLayout(jpPropertyName = "実働時間", layout = "A")
	private RegularAndIrregularTimeOfMonthlyDto actualWorkingTime;

	/** 集計時間: 集計総労働時間 */
	@AttendanceItemLayout(jpPropertyName = "集計時間", layout = "A")
	private AggregateTotalWorkingTimeDto aggregateTime;

	/** 総拘束時間: 期間別の総拘束時間 */
	@AttendanceItemLayout(jpPropertyName = "総拘束時間", layout = "A")
	private AggregateTotalTimeSpentAtWorkDto totalTimeSpentAtWork;

	/** 総労働時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "総労働時間", layout = "A")
	private Integer totalWorkingTime;

	/** 法定労働時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定労働時間", layout = "A")
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
