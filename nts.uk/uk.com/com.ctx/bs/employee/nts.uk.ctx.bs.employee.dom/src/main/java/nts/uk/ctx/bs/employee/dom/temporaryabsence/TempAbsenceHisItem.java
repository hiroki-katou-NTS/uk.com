/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.GenericString;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.LeaveHolidayType;

/**
 * @author danpv
 * Domain Name : 休職休業履歴項目
 *
 */
@Getter
public class TempAbsenceHisItem extends AggregateRoot{
	
	private LeaveHolidayType leaveHolidayType;
	
	private String historyId;
	
	private String employeeId;
	
	// ------------- Optional ----------------
	
	/**
	 * 備考
	 */
	private GenericString remarks;
	
	/**
	 * 社会保険支給対象区分
	 */
	private Integer soInsPayCategory;

}
