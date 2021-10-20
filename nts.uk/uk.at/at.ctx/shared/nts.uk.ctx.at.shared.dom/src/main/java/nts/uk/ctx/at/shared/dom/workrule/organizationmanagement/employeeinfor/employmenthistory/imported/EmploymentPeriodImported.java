package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;


/**
 * «Imported» 社員の雇用期間
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.組織管理.社員情報.所属雇用履歴
 * @author HieuLt
 */
@Getter
@RequiredArgsConstructor
public class EmploymentPeriodImported {

	/**社員ID **/ 
	private final String empID; 
	/**期間 **/ 
	private final DatePeriod datePeriod;
	/**雇用コード **/
	private final String employmentCd;
	/**給与区分 **/
	private final Optional<SalarySegment> otpSalarySegment; 
	
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


