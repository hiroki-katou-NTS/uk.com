package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import lombok.Value;
import nts.arc.time.GeneralDate;
@Value
public class EmployeeUnregisterOutput {
	/** The CompanyId */
	private String companyId;

	/** The personId */
	private String pId;

	/** The employeeId */
	private String sId;

	/** The employeeCode */
	private String sCd;

	/** The Company Mail */
	private String companyMail;

	/** The Company Mobile Mail - 会社携帯メールアドレス */
	private String mobileMail;

	/** The Company Mobile */
	private String companyMobile;

	/** TheJob Entry History */
	private String jobEntryHistory;

	/** The HiringType */
	private int hiringType;

	/** The RetireDate */
	private GeneralDate retirementDate;

	/** The EntryDate */
	private GeneralDate joinDate;

	/** The AdoptDate */
	private GeneralDate adoptDate;
}
