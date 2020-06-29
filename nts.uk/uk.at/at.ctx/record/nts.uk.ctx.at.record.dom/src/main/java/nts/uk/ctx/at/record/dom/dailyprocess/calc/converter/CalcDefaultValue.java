package nts.uk.ctx.at.record.dom.dailyprocess.calc.converter;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.AbsenceOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.AnnualOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.OverSalaryOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.SpecialHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.TimeDigestOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.YearlyReservedOfDaily;

public class CalcDefaultValue {

	public static final AttendanceTime DEFAULT_TIME = new AttendanceTime(0);
	
	public static final VacationClass DEFAULT_VACATION = new VacationClass(new HolidayOfDaily(new AbsenceOfDaily(DEFAULT_TIME),
			new TimeDigestOfDaily(DEFAULT_TIME, DEFAULT_TIME),
			new YearlyReservedOfDaily(DEFAULT_TIME),
			new SubstituteHolidayOfDaily(DEFAULT_TIME, DEFAULT_TIME),
			new OverSalaryOfDaily(DEFAULT_TIME, DEFAULT_TIME),
			new SpecialHolidayOfDaily(DEFAULT_TIME, DEFAULT_TIME),
			new AnnualOfDaily(DEFAULT_TIME, DEFAULT_TIME)));;
}
