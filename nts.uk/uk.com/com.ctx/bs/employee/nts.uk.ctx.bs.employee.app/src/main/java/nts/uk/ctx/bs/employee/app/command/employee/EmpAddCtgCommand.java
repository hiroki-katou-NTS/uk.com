package nts.uk.ctx.bs.employee.app.command.employee;

import java.util.List;

import lombok.Data;

@Data
public class EmpAddCtgCommand {

	private String categoryCd;

	private String categoryId;

	private List<EmpAddItemCommand> listItem;

}
