package nts.uk.ctx.bs.employee.app.command.person.category;

import lombok.Data;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemOptionalDto;

@Data
public class RegisterPerInfoMultiCommand {
	private String employeeId;
	private String perInfoCtgId;
	private String infoId;
	private CtgItemOptionalDto ctgItemOptionalDto;
	private CtgItemFixDto ctgItemFixDto;
}
