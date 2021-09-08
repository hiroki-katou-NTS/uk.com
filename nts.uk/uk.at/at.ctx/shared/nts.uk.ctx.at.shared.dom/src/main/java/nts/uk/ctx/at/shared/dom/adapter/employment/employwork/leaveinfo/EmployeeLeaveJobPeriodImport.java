package nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo;

import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 社員の休職期間 Imported
 * 
 * @author HieuLt
 *
 */

@Getter
public class EmployeeLeaveJobPeriodImport {
	/** 社員ID **/

	private final String empID;
	/** 期間 **/
	private final DatePeriod datePeriod;

	// [C-0] 社員の休職期間Imported( 社員ID, 期間 )
	public EmployeeLeaveJobPeriodImport(String empID, DatePeriod datePeriod) {
		super();
		this.empID = empID;
		this.datePeriod = datePeriod;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (obj instanceof EmployeeLeaveJobPeriodImport) {
			EmployeeLeaveJobPeriodImport other = (EmployeeLeaveJobPeriodImport) obj;
			if (empID == null) {
				if (other.getEmpID()!= null)
					return false;
			} else if (!empID.equals(other.getEmpID()))
				return false;
			
			if (datePeriod == null) {
				if (other.getDatePeriod()!= null)
					return false;
			}
			
			if (datePeriod != null) {
				if (other.getDatePeriod()== null)
					return false;
			}
			
			if (!datePeriod.start().equals(other.getDatePeriod().start())){
				return false;
			}  
			
			if (!datePeriod.end().equals(other.getDatePeriod().end())){
				return false;
			}
			
			return ((EmployeeLeaveJobPeriodImport) obj).getEmpID().equals(empID)
					&& ((EmployeeLeaveJobPeriodImport) obj).getDatePeriod().start().equals(datePeriod.start())
					&& ((EmployeeLeaveJobPeriodImport) obj).getDatePeriod().end().equals(datePeriod.end());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empID == null) ? 0 : empID.hashCode());
		result = prime * result + ((datePeriod == null) ? 0 : datePeriod.start().hashCode());
		result = prime * result + ((datePeriod == null) ? 0 : datePeriod.start().hashCode());
		return result;
	}
}
