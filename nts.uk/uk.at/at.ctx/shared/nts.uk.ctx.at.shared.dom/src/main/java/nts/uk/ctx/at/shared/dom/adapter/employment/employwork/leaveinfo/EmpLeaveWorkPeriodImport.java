package nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo;

import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 *社員の休業期間 Import
 * @author HieuLt
 * 
 */

@Getter
public class EmpLeaveWorkPeriodImport {
	
	/** 社員ID**/
	private final	String empID;
	
	/** 休職休業枠NO */
	
	private final TempAbsenceFrameNo tempAbsenceFrNo; 
	
	/** 期間 **/ 
	private  final DatePeriod datePeriod;

	//[C-0] 社員の休業期間Imported( 社員ID, 期間, 休職休業枠NO )		
	public EmpLeaveWorkPeriodImport(String empID, TempAbsenceFrameNo tempAbsenceFrNo, DatePeriod datePeriod) {
		super();
		this.empID = empID;
		this.tempAbsenceFrNo = tempAbsenceFrNo;
		this.datePeriod = datePeriod;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (obj instanceof EmpLeaveWorkPeriodImport) {
			EmpLeaveWorkPeriodImport other = (EmpLeaveWorkPeriodImport) obj;
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
			
			return ((EmpLeaveWorkPeriodImport) obj).getEmpID().equals(empID)
					&& ((EmpLeaveWorkPeriodImport) obj).getDatePeriod().start().equals(datePeriod.start())
					&& ((EmpLeaveWorkPeriodImport) obj).getDatePeriod().end().equals(datePeriod.end());
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
