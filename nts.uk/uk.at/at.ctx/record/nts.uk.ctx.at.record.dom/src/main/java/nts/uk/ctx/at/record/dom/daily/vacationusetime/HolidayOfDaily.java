package nts.uk.ctx.at.record.dom.daily.vacationusetime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の休暇
 * @author keisuke_hoshina
 *
 */
@Getter
public class HolidayOfDaily {

	//欠勤
	private AbsenceOfDaily absence;
	//時間消化休暇
	private TimeDigestOfDaily timeDigest;
	//積立年休
	private YearlyReservedOfDaily yearlyReserved;
	//代休
	private SubstituteHolidayOfDaily substitute;
	//超過有休
	private OverSalaryOfDaily overSalary;
	//特別休暇
	private SpecialHolidayOfDaily specialHoliday;
	//年休
	private AnnualOfDaily annual;
	
	/**
	 * Constructor 
	 */
	public HolidayOfDaily(AbsenceOfDaily absence, TimeDigestOfDaily timeDigest, YearlyReservedOfDaily yearlyReserved,
			SubstituteHolidayOfDaily substitute, OverSalaryOfDaily overSalary, SpecialHolidayOfDaily specialHoliday,
			AnnualOfDaily annual) {
		super();
		this.absence = absence;
		this.timeDigest = timeDigest;
		this.yearlyReserved = yearlyReserved;
		this.substitute = substitute;
		this.overSalary = overSalary;
		this.specialHoliday = specialHoliday;
		this.annual = annual;
	}
	
	public AttendanceTime calcTotalHolTime() {
		return new AttendanceTime(this.getAnnual().getUseTime().valueAsMinutes()
								+ this.getSubstitute().getUseTime().valueAsMinutes()
								+ this.getSpecialHoliday().getUseTime().valueAsMinutes()
								+ this.getOverSalary().getUseTime().valueAsMinutes()
								+ this.getYearlyReserved().getUseTime().valueAsMinutes());
			 
	}
	
	
}
