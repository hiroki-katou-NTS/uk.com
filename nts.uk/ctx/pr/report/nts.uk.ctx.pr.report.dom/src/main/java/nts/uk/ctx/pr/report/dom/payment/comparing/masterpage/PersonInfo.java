package nts.uk.ctx.pr.report.dom.payment.comparing.masterpage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.EmployeeCode;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.EmployeeName;

@AllArgsConstructor
@Getter
public class PersonInfo extends AggregateRoot {

	private EmployeeCode employeeCode;

	private EmployeeName employeeName;

	public static PersonInfo createFromJavaType(String employeeCode, String employeeName) {
		return new PersonInfo(new EmployeeCode(employeeCode), new EmployeeName(employeeName));
	}
}
