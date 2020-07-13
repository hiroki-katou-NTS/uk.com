package nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
/**
 *社員の休業期間
 * @author HieuLt
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmpLeaveWorkPeriodImport {
	
	/** 社員ID**/
	private	String empID;
	
	/** 休職休業枠NO */
	private int tempAbsenceFrNo; 
	
	/** 期間 **/ 
	private DatePeriod datePeriod;
}
