package nts.uk.ctx.at.record.app.find.monthly.week;

import java.util.Optional;

import nts.uk.ctx.at.record.app.find.monthly.root.AttendanceTimeOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.ExcessOutsideWorkOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.MonthlyCalculationDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.TotalCountByPeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.VerticalTotalOfMonthlyDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;

public class WeeklyAttendanceTimeDto extends AttendanceTimeOfMonthlyDto {

	/***/
	private static final long serialVersionUID = 1L;
	
	public static WeeklyAttendanceTimeDto from(AttendanceTimeOfWeekly domain) {
		
		WeeklyAttendanceTimeDto dto = new WeeklyAttendanceTimeDto();
		
		if(domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYm(domain.getYearMonth());
			dto.setClosureID(domain.getClosureId() == null ? 1 : domain.getClosureId().value);
			dto.setClosureDate(domain.getClosureDate() == null ? null : ClosureDateDto.from(domain.getClosureDate()));
			dto.setDatePeriod(domain.getPeriod() == null ? null : new DatePeriodDto(domain.getPeriod().start(), domain.getPeriod().end()));
			dto.setMonthlyCalculation(MonthlyCalculationDto.from(domain.getWeeklyCalculation()));
			dto.setAggregateDays(domain.getAggregateDays() == null ? 0 : domain.getAggregateDays().v());
			dto.setVerticalTotal(VerticalTotalOfMonthlyDto.from(domain.getVerticalTotal()));
			dto.setTotalCount(TotalCountByPeriodDto.from(domain.getTotalCount()));
			dto.setVersion(domain.getVersion());
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case CALC:
//		case EXCESS:
		case VERTICAL_TOTAL:
		case (COUNT + AGGREGATE):
			return super.newInstanceOf(path);
		default:
			break;
		}
		return null;
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case CALC:
//		case EXCESS:
		case VERTICAL_TOTAL:
		case (COUNT + AGGREGATE):
			return super.get(path);
		default:
			break;
		}
		return Optional.empty();
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case CALC:
//		case EXCESS:
		case VERTICAL_TOTAL:
		case (COUNT + AGGREGATE):
			super.set(path, value);
		default:
			break;
		}
	}
}
