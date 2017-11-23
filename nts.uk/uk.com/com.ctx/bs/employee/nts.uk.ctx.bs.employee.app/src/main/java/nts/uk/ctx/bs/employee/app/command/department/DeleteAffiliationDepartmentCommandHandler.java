package nts.uk.ctx.bs.employee.app.command.department;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;
@Stateless
public class DeleteAffiliationDepartmentCommandHandler extends CommandHandler<DeleteAffiliationDepartmentCommand>
	implements PeregDeleteCommandHandler<DeleteAffiliationDepartmentCommand>{

	@Inject
	private AffDepartmentRepository affDepartmentRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00011";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteAffiliationDepartmentCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteAffiliationDepartmentCommand> context) {
		val command = context.getCommand();
		
		affDepartmentRepository.deleteAffDepartment(command.getId());
	}

}
