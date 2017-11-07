package nts.uk.ctx.bs.employee.app.command.employee;

import java.util.List;

import lombok.Data;

@Data
public class EmpAddCommand {

	private String employeeName;
	private String employeeCode;
	private String hireDate;
	private String cardNo;
	private String avatarId;
	private List<EmpAddCtgCommand> listCtg;

}
