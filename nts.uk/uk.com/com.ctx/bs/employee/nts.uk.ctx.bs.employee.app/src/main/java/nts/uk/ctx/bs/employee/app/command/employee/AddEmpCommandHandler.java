package nts.uk.ctx.bs.employee.app.command.employee;

import java.util.List;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.app.find.init.item.SettingItemDto;
import nts.uk.ctx.bs.employee.app.find.layout.LayoutFinder;

public class AddEmpCommandHandler extends CommandHandler<AddEmpCommand> {
	@Inject
	private LayoutFinder layoutFinder;

	@Override
	protected void handle(CommandHandlerContext<AddEmpCommand> context) {

		AddEmpCommand command = context.getCommand();

		List<SettingItemDto> dataList = this.layoutFinder.loadAllItemByCreateType(command.getCreateType(),
				command.getInitSettingId(), command.getHireDate(), command.getEmployeeCopyId());

	}

}
