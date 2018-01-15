package nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class EmployeeImport {
	/** The company id. */
	private String companyId;

	/** The p id. */
	private String pId;

	/** The s id. */
	private String sId;

	/** The s cd. */
	private String sCd;

	/** The s mail. */
	private String sMail;

	/** The retirement date. */
	private GeneralDate retirementDate;

	/** The join date. */
	private GeneralDate joinDate;
}
