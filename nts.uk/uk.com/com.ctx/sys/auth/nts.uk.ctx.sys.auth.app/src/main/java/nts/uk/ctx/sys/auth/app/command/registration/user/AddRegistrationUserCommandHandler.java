package nts.uk.ctx.sys.auth.app.command.registration.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.security.hash.password.PasswordHash;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.dom.registration.user.service.RegistrationUserService;
import nts.uk.ctx.sys.auth.dom.user.HashPassword;
import nts.uk.ctx.sys.auth.dom.user.LoginID;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AddRegistrationUserCommandHandler.
 */
@Stateless
@Transactional
public class AddRegistrationUserCommandHandler extends CommandHandlerWithResult<AddRegistrationUserCommand, String> {

	/** The user repo. */
	@Inject
	private UserRepository userRepo;

	/** The password change log repository. */
	@Inject
	private PasswordChangeLogRepository passwordChangeLogRepository;

	/** The registration user service. */
	@Inject
	private RegistrationUserService registrationUserService;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandlerWithResult#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected String handle(CommandHandlerContext<AddRegistrationUserCommand> context) {
		AddRegistrationUserCommand command = context.getCommand();
		String contractCode = AppContexts.user().contractCode();
		String personalId = command.getAssociatedPersonID();
		String password = command.getPassword();
		String userId = IdentifierUtil.randomUniqueId();
		// check if loginId is existed
		List<User> userList = userRepo.getByContractCode(contractCode);
		List<LoginID> loginIDs = userList.stream().map(u -> new LoginID(u.getLoginID().toString()))
				.collect(Collectors.toList());
		LoginID currentLoginID = new LoginID(command.getLoginID());
		if (loginIDs.contains(currentLoginID)) {
			throw new BusinessException("Msg_61", currentLoginID.toString());
		}

		if (personalId != null && !userRepo.getByContractAndPersonalId(contractCode, personalId).isEmpty()) {
			throw new BusinessException("Msg_716");
		}

		// password policy check
		BundledBusinessException bundledBusinessExceptions = registrationUserService.getMsgCheckPasswordPolicy(userId, password, contractCode);
		if (bundledBusinessExceptions != null) {
			// Throw error list
			if (!bundledBusinessExceptions.cloneExceptions().isEmpty()) {
				throw bundledBusinessExceptions;
			}
		}
		String newPassHash = PasswordHash.generate(password, userId);
		HashPassword hashPW = new HashPassword(newPassHash);

		// register password change log
		// get domain PasswordChangeLog
		PasswordChangeLog passwordChangeLog = new PasswordChangeLog(currentLoginID.toString(), userId,
				GeneralDateTime.now(), hashPW);
		passwordChangeLogRepository.add(passwordChangeLog);
		// register user
		User newUser = User.createFromJavatype(userId, false, hashPW.toString(), command.getLoginID(), contractCode,
				GeneralDate.fromString(command.getExpirationDate(), "yyyy/MM/dd"), command.isSpecialUser() ? 1 : 0, command.isMultiCompanyConcurrent() ? 1 : 0, command.getMailAddress() == null ? null : command.getMailAddress(),
				command.getUserName(), command.getAssociatedPersonID() == null ? null : command.getAssociatedPersonID(), 1, 0);
		userRepo.addNewUser(newUser);

		return userId;
	}

}
