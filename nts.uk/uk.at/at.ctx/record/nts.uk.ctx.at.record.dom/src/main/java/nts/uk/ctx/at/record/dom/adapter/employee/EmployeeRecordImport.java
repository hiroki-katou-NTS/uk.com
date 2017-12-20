package nts.uk.ctx.at.record.dom.adapter.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRecordImport {
	private String pid;

	private String pname;

	private GeneralDate entryDate;

	private int gender;

	private GeneralDate birthDay;

	/** The employee id. */
	private String employeeId;

	/** The employee code. */
	private String employeeCode;

	private GeneralDate retiredDate;
}
