package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.midnighttime.IllegalMidnightTime;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.midnighttime.MidnightTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の深夜時間 */
public class MidnightTimeOfMonthlyDto {

	/** 残業深夜時間 */
	@AttendanceItemLayout(jpPropertyName = "残業深夜時間", layout = "A")
	private TimeMonthWithCalculationDto overWorkMidnightTime;

	/** 法定内深夜時間 */
	@AttendanceItemLayout(jpPropertyName = "法定内深夜時間", layout = "B")
	private TimeMonthWithCalculationDto legalMidnightTime;

	/** 法定外深夜時間 */
	@AttendanceItemLayout(jpPropertyName = "法定外深夜時間", layout = "C")
	private IllegalMidnightTimeDto illegalMidnightTime;

	/** 法定内休出深夜時間 */
	@AttendanceItemLayout(jpPropertyName = "法定内休出深夜時間", layout = "D")
	private TimeMonthWithCalculationDto legalHolidayWorkMidnightTime;

	/** 法定外休出深夜時間 */
	@AttendanceItemLayout(jpPropertyName = "法定外休出深夜時間", layout = "E")
	private TimeMonthWithCalculationDto illegalHolidayWorkMidnightTime;

	/** 祝日休出深夜時間 */
	@AttendanceItemLayout(jpPropertyName = "祝日休出深夜時間", layout = "F")
	private TimeMonthWithCalculationDto specialHolidayWorkMidnightTime;

	public static MidnightTimeOfMonthlyDto from(MidnightTimeOfMonthly domain) {
		MidnightTimeOfMonthlyDto dto = new MidnightTimeOfMonthlyDto();
		if(domain != null) {
			dto.setIllegalHolidayWorkMidnightTime(TimeMonthWithCalculationDto.from(domain.getIllegalHolidayWorkMidnightTime()));
			dto.setIllegalMidnightTime(IllegalMidnightTimeDto.from(domain.getIllegalMidnightTime()));
			dto.setLegalHolidayWorkMidnightTime(TimeMonthWithCalculationDto.from(domain.getLegalHolidayWorkMidnightTime()));
			dto.setLegalMidnightTime(TimeMonthWithCalculationDto.from(domain.getLegalMidnightTime()));
			dto.setOverWorkMidnightTime(TimeMonthWithCalculationDto.from(domain.getOverWorkMidnightTime()));
			dto.setSpecialHolidayWorkMidnightTime(TimeMonthWithCalculationDto.from(domain.getSpecialHolidayWorkMidnightTime()));
		}
		return dto;
	}
	
	public MidnightTimeOfMonthly toDomain() {
		return MidnightTimeOfMonthly.of(overWorkMidnightTime == null ? new TimeMonthWithCalculation() : overWorkMidnightTime.toDomain(), 
										legalMidnightTime == null ? new TimeMonthWithCalculation() : legalMidnightTime.toDomain(),  
										illegalMidnightTime == null ? new IllegalMidnightTime() : illegalMidnightTime.toDomain(), 
										legalHolidayWorkMidnightTime == null ? new TimeMonthWithCalculation() : legalHolidayWorkMidnightTime.toDomain(), 
										illegalHolidayWorkMidnightTime == null ? new TimeMonthWithCalculation() : illegalHolidayWorkMidnightTime.toDomain(), 
										specialHolidayWorkMidnightTime == null ? new TimeMonthWithCalculation() : specialHolidayWorkMidnightTime.toDomain());
	}
}
