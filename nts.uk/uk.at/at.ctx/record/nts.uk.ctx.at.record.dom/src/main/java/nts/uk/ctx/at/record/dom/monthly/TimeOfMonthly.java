package nts.uk.ctx.at.record.dom.monthly;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeOfMonthly {

	private Optional<AttendanceTimeOfMonthly> attendanceTime;
	
	private Optional<AffiliationInfoOfMonthly> affiliation;
	
	public TimeOfMonthly(AttendanceTimeOfMonthly attendanceTime, AffiliationInfoOfMonthly affiliation){
		this.attendanceTime = Optional.ofNullable(attendanceTime);
		this.affiliation = Optional.ofNullable(affiliation);
	}
	
	public ClosureDate getClosureDate() {
		if(affiliation.isPresent()){
			return affiliation.get().getClosureDate();
		}
		
		return attendanceTime.get().getClosureDate();
	}
	
	public String getEmployeeId() {
		if(affiliation.isPresent()){
			return affiliation.get().getEmployeeId();
		}
		
		return attendanceTime.get().getEmployeeId();
	}
	
	public YearMonth getYearMonth() {
		if(affiliation.isPresent()){
			return affiliation.get().getYearMonth();
		}
		
		return attendanceTime.get().getYearMonth();
	}
	
	public ClosureId getClosureId() {
		if(affiliation.isPresent()){
			return affiliation.get().getClosureId();
		}
		
		return attendanceTime.get().getClosureId();
	}
}
