package nts.uk.ctx.bs.employee.dom.employeeinfo;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 
 *  JobEntryHistory - 入社履歴
 */

@Getter
@AllArgsConstructor
public class JobEntryHistory extends ValueObject{
	
	/** The CompanyId */
	private String companyId;

	/** The employeeId */
	private String sId;
	
	/** The HiringType */
	private HiringType hiringType;

	/** The RetireDate */
	private GeneralDate retirementDate;

	/** The EntryDate */
	private GeneralDate joinDate;

	/** The AdoptDate */
	private GeneralDate adoptDate;

}
