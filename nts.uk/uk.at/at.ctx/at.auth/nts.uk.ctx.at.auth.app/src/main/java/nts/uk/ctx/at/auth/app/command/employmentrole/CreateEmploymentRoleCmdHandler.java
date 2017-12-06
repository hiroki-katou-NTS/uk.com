package nts.uk.ctx.at.auth.app.command.employmentrole;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.auth.dom.employmentrole.EmploymentRole;
import nts.uk.ctx.at.auth.dom.employmentrole.EmploymentRoleRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CreateEmploymentRoleCmdHandler extends CommandHandler<CreateEmploymentRoleCmd> {

	@Inject
	private EmploymentRoleRepository empRepo;
	
	@Override
	protected void handle(CommandHandlerContext<CreateEmploymentRoleCmd> context) {
		CreateEmploymentRoleCmd appCommand =  context.getCommand();
		EmploymentRole employmentRole = appCommand.toDomain();
		Optional<EmploymentRole> emp = empRepo.getEmploymentRoleById(appCommand.getCompanyId(), appCommand.getRoleId());
		if(emp.isPresent()) {
			throw new BusinessException("Msg_3");
		}else {
			empRepo.addEmploymentRole(employmentRole);
		}
		
	}

}
