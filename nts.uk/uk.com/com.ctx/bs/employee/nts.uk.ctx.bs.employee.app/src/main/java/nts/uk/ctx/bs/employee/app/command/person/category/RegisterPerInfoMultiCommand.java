package nts.uk.ctx.bs.employee.app.command.person.category;

import lombok.Data;

@Data
public class RegisterPerInfoMultiCommand {
	private String employeeId;
	private String perInfoCtgId;
	private String infoId;
}
