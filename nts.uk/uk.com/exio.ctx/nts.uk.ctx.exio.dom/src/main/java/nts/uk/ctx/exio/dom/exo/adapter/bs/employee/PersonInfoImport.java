package nts.uk.ctx.exio.dom.exo.adapter.bs.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
public class PersonInfoImport {
	private String pid;

	private String businessName;

	private GeneralDate entryDate;

	private int gender;

	private GeneralDate birthDay;

	/** The employee id. */
	private String employeeId;

	/** The employee code. */
	private String employeeCode;

	private GeneralDate retiredDate;
}
