package nts.uk.ctx.sys.auth.app.command.registration.user;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.registration.user.service.RegistrationUserService;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DeleteRegistrationUserCommandHandler extends CommandHandlerWithResult<DeleteRegistrationUserCommand, String> {
	
	@Inject UserRepository userRepo;
	
	@Inject RegistrationUserService registrationUserService;

	@Override
	protected String handle(CommandHandlerContext<DeleteRegistrationUserCommand> context) {
		DeleteRegistrationUserCommand command = context.getCommand();
		String userId = command.getUserID();
		
		// check system admin
		if(!registrationUserService.checkSystemAdmin(command.getUserID(), GeneralDate.ymd(1990,1,1)))
			throw new BusinessException("Msg_331");
		
		// check pre-constraints on deletion
		if(userId.equals(AppContexts.user().userId()))
			throw new BusinessException("Msg_1354");
		if(command.getPersonalId() != null)
			throw new BusinessException("Msg_481");
		
		userRepo.delete(userId);
		return userId;
	}

}
