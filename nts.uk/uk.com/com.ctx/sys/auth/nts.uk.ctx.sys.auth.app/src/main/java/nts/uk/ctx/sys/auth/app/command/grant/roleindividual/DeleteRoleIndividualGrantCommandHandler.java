package nts.uk.ctx.sys.auth.app.command.grant.roleindividual;

import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.grant.service.RoleIndividualService;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public class DeleteRoleIndividualGrantCommandHandler extends CommandHandler<DeleteRoleIndividualGrantCommand> {

	@Inject
	private RoleIndividualService roleIndividualService;

	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteRoleIndividualGrantCommand> context) {
		DeleteRoleIndividualGrantCommand command = context.getCommand();
        
		if(command.getRoleType() == RoleType.SYSTEM_MANAGER){
			DatePeriod insertPeriod = new DatePeriod(command.getStartDate(),command.getEndDate());
			boolean isValid = roleIndividualService.checkSysAdmin(command.getUserId(), insertPeriod);
		    if(isValid == true)
			throw new BusinessException("Msg_331");
		}
		roleIndividualGrantRepo.remove(command.getUserId(), command.getCompanyId(), command.getRoleType());
	}
	

}
