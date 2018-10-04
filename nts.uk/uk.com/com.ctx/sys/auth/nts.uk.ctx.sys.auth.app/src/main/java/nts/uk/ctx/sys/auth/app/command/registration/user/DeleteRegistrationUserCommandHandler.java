package nts.uk.ctx.sys.auth.app.command.registration.user;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.registration.user.service.RegistrationUserService;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteRegistrationUserCommandHandler.
 */
@Stateless
@Transactional
public class DeleteRegistrationUserCommandHandler
		extends CommandHandler<DeleteRegistrationUserCommand> {

	/** The user repo. */
	@Inject
	UserRepository userRepo;

	/** The registration user service. */
	@Inject
	RegistrationUserService registrationUserService;
	
	@Inject
	RoleIndividualGrantRepository roleIndividualGrantRepo;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandlerWithResult#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteRegistrationUserCommand> context) {
		DeleteRegistrationUserCommand command = context.getCommand();
		String userId = command.getUserID();

		// check system admin
		if (!registrationUserService.checkSystemAdmin(command.getUserID(), GeneralDate.ymd(1990, 1, 1)))
			throw new BusinessException("Msg_331");

		// check pre-constraints on deletion
		if (userId.equals(AppContexts.user().userId())) {
			throw new BusinessException("Msg_1354");
		}
		if (command.getPersonalId() != null) {
			throw new BusinessException("Msg_481");
		}

		userRepo.delete(userId);
		roleIndividualGrantRepo.removeByUserId(userId);
	}

}
