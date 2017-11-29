package nts.uk.ctx.bs.employee.app.command.department;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateAffiliationDepartmentCommandHandler extends CommandHandler<UpdateAffiliationDepartmentCommand>
	implements PeregUpdateCommandHandler<UpdateAffiliationDepartmentCommand>{

	@Inject
	private AffDepartmentRepository affDepartmentRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00011";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateAffiliationDepartmentCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateAffiliationDepartmentCommand> context) {
		val command = context.getCommand();
		
		AffiliationDepartment domain = AffiliationDepartment.createDmainFromJavaType(command.getId(), command.getStartDate(), command.getEndDate(), command.getEmployeeId(), command.getDepartmentId());
		
		affDepartmentRepository.updateAffDepartment(domain);
	}

}
