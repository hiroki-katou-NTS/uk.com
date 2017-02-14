package nts.uk.ctx.basic.app.command.organization.department;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.department.DepartmentCode;
import nts.uk.ctx.basic.dom.organization.department.DepartmentDomainService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemoveDepartmentCommandHandler extends CommandHandler<RemoveDepartmentCommand> {

	@Inject
	private DepartmentDomainService departmentDomainService;

	@Override
	protected void handle(CommandHandlerContext<RemoveDepartmentCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		departmentDomainService.remove(companyCode, new DepartmentCode(context.getCommand().getDepartmentCode()),
				context.getCommand().getHistoryId());

	}

}
