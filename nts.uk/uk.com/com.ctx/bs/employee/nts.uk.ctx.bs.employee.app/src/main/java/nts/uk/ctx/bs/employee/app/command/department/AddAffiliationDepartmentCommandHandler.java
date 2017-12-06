package nts.uk.ctx.bs.employee.app.command.department;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddAffiliationDepartmentCommandHandler extends CommandHandlerWithResult<AddAffiliationDepartmentCommand,PeregAddCommandResult>
	implements PeregAddCommandHandler<AddAffiliationDepartmentCommand>{

	@Inject
	private AffDepartmentRepository affDepartmentRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00011";
	}

	@Override
	public Class<?> commandClass() {
		return AddAffiliationDepartmentCommand.class;
	}

	@Override
	protected PeregAddCommandResult  handle(CommandHandlerContext<AddAffiliationDepartmentCommand> context) {
		val command = context.getCommand();
		
		String newId = IdentifierUtil.randomUniqueId();
		
		AffiliationDepartment domain = AffiliationDepartment.createDmainFromJavaType(newId, command.getStartDate(), command.getEndDate(), command.getEmployeeId(), command.getDepartmentId());
		affDepartmentRepository.addAffDepartment(domain);
		
		return new PeregAddCommandResult(newId);
	}

}
