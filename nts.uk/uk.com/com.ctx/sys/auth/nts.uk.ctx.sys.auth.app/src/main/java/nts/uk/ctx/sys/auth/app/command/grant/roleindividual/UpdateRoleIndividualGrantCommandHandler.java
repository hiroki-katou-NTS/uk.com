package nts.uk.ctx.sys.auth.app.command.grant.roleindividual;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.grant.service.RoleIndividualService;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
@Transactional
public class UpdateRoleIndividualGrantCommandHandler extends CommandHandler<UpdateRoleIndividualGrantCommand> {

	@Inject
	private RoleRepository roleRepository;
	
	@Inject
	private RoleIndividualService roleIndividualService;
	
	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateRoleIndividualGrantCommand> context) {
		UpdateRoleIndividualGrantCommand command = context.getCommand();

		if (command.getUserID().isEmpty()) {
			throw new BusinessException("Msg_218");
		}
		
		Role sysAdminRole = roleRepository.findByType(RoleType.SYSTEM_MANAGER.value).get(0);
		DatePeriod insertPeriod = new DatePeriod(command.getStartValidPeriod(),command.getEndValidPeriod());
		boolean isValid = roleIndividualService.checkSysAdmin(command.getUserID(), insertPeriod);
		if (isValid == false){
			throw new BusinessException("Msg_330");
		}
		roleIndividualGrantRepo.update(command.toDomain(sysAdminRole.getRoleId()));

	}

}
