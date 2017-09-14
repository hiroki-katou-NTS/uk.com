package nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee;

import lombok.Data;
import nts.arc.time.GeneralDate;
/**
 * The Class EmployeeDto.
 */
@Data
public class EmployeeApproveDto {

	/** The company id. */
	private String companyId;

	/** The p id. */
	private String pId;

	/** The s id. */
	private String sId;

	/** The s cd. */
	private String sCd;
	/**
	 * name
	 */
	private String pName;
	
	private String wpCode;
	
	private String wpName;
	/** The s mail. */
	private String sMail;

	/** The retirement date. */
	private GeneralDate retirementDate;

	/** The join date. */
	private GeneralDate joinDate;
}
