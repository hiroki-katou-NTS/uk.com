package nts.uk.ctx.pr.report.dom.payment.comparing.masterpage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.EmployeeCode;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.EmployeeName;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PersonID;

@AllArgsConstructor
@Getter
public class PersonInfo extends AggregateRoot {

	private PersonID personID;

	private EmployeeCode employeeCode;

	private EmployeeName employeeName;

	private DepartmentCode departmentCode;

	private DepartmentName departmentName;

	public static PersonInfo createFromJavaType(String personID, String employeeCode, String employeeName,
			String departmentCode, String departmentName) {
		return new PersonInfo(new PersonID(personID), new EmployeeCode(employeeCode), new EmployeeName(employeeName),
				new DepartmentCode(departmentCode), new DepartmentName(departmentName)

		);
	}
}
