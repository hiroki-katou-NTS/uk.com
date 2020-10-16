package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.midnighttime.IllegalMidnightTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.midnighttime.MidnightTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の深夜時間 */
public class MidnightTimeOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 残業深夜時間 */
	@AttendanceItemLayout(jpPropertyName = OVERTIME, layout = LAYOUT_A)
	private TimeMonthWithCalculationDto overWorkMidnightTime;

	/** 法定内深夜時間 */
	@AttendanceItemLayout(jpPropertyName = LEGAL, layout = LAYOUT_B)
	private TimeMonthWithCalculationDto legalMidnightTime;

	/** 法定外深夜時間 */
	@AttendanceItemLayout(jpPropertyName = ILLEGAL, layout = LAYOUT_C)
	private IllegalMidnightTimeDto illegalMidnightTime;

	/** 法定内休出深夜時間 */
	@AttendanceItemLayout(jpPropertyName = LEGAL + HOLIDAY_WORK, layout = LAYOUT_D)
	private TimeMonthWithCalculationDto legalHolidayWorkMidnightTime;

	/** 法定外休出深夜時間 */
	@AttendanceItemLayout(jpPropertyName = ILLEGAL + HOLIDAY_WORK, layout = LAYOUT_E)
	private TimeMonthWithCalculationDto illegalHolidayWorkMidnightTime;

	/** 祝日休出深夜時間 */
	@AttendanceItemLayout(jpPropertyName = PUBLIC_HOLIDAY, layout = LAYOUT_F)
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

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case OVERTIME:
		case LEGAL:
		case (LEGAL + HOLIDAY_WORK):
		case (ILLEGAL + HOLIDAY_WORK):
		case PUBLIC_HOLIDAY:
			return new TimeMonthWithCalculationDto();
		case ILLEGAL:
			return new IllegalMidnightTimeDto();
		default:
			return null;
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case OVERTIME:
			return Optional.ofNullable(overWorkMidnightTime);
		case LEGAL:
			return Optional.ofNullable(legalMidnightTime);
		case ILLEGAL:
			return Optional.ofNullable(illegalMidnightTime);
		case (LEGAL + HOLIDAY_WORK):
			return Optional.ofNullable(legalHolidayWorkMidnightTime);
		case (ILLEGAL + HOLIDAY_WORK):
			return Optional.ofNullable(illegalHolidayWorkMidnightTime);
		case PUBLIC_HOLIDAY:
			return Optional.ofNullable(specialHolidayWorkMidnightTime);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case OVERTIME:
			overWorkMidnightTime = (TimeMonthWithCalculationDto) value; break;
		case LEGAL:
			legalMidnightTime = (TimeMonthWithCalculationDto) value; break;
		case ILLEGAL:
			illegalMidnightTime = (IllegalMidnightTimeDto) value; break;
		case (LEGAL + HOLIDAY_WORK):
			legalHolidayWorkMidnightTime = (TimeMonthWithCalculationDto) value; break;
		case (ILLEGAL + HOLIDAY_WORK):
			illegalHolidayWorkMidnightTime = (TimeMonthWithCalculationDto) value; break;
		case PUBLIC_HOLIDAY:
			specialHolidayWorkMidnightTime = (TimeMonthWithCalculationDto) value; break;
		default:
		}
	}
}
