package nts.uk.ctx.bs.employee.app.command.category;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.bs.employee.app.command.employee.LayoutPersonInfoCommand;
@Data
public class AddCategoryCommand {
	private String employeeId;
	private List<LayoutPersonInfoCommand> items;
}
