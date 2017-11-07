package nts.uk.ctx.bs.employee.app.command.employee;

import lombok.Data;

@Data
public class EmpAddItemCommand {

	private String itemCode;
	
	private String itemId;

	private String value;

}
