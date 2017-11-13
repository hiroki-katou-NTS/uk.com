package nts.uk.ctx.bs.employee.app.command.category;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.bs.employee.app.command.employee.LayoutPersonInfoCommand;
@Data
public class UpdateCategoryCommand {
	private String employeeId;
	private String infoId;
	private List<LayoutPersonInfoCommand> items;
}
