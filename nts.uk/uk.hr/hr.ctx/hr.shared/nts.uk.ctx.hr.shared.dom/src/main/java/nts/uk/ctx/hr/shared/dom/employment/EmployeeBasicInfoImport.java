package nts.uk.ctx.hr.shared.dom.employment;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Builder
@Getter
public class EmployeeBasicInfoImport {
	private String employmentCode;

	private GeneralDate dateJoinComp;

	private String sid;

	private GeneralDate birthday;

	public String pid;
}
