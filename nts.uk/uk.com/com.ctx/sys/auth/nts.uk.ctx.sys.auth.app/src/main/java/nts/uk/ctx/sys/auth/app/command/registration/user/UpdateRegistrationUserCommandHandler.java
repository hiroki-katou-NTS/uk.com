package nts.uk.ctx.sys.auth.app.command.registration.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.dom.registration.user.service.RegistrationUserService;
import nts.uk.ctx.sys.auth.dom.user.DisabledSegment;
import nts.uk.ctx.sys.auth.dom.user.HashPassword;
import nts.uk.ctx.sys.auth.dom.user.LoginID;
import nts.uk.ctx.sys.auth.dom.user.MailAddress;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserName;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;

/**
 * The Class UpdateRegistrationUserCommandHandler.
 */
@Stateless
@Transactional
public class UpdateRegistrationUserCommandHandler
		extends CommandHandler<UpdateRegistrationUserCommand> {

	/** The user repo. */
	@Inject
	private UserRepository userRepo;

	/** The registration user service. */
	@Inject
	private RegistrationUserService registrationUserService;

	/** The password change log repository. */
	@Inject
	private PasswordChangeLogRepository passwordChangeLogRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandlerWithResult#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateRegistrationUserCommand> context) {
		UpdateRegistrationUserCommand command = context.getCommand();
		String userId = command.getUserID();
		String personalId = command.getAssociatedPersonID();
		User updateUser = userRepo.getByUserID(userId).get();
		String contractCode = updateUser.getContractCode().toString();
		String password = command.getPassword();
		GeneralDate validityPeriod = GeneralDate.fromString(command.getExpirationDate(), "yyyy/MM/dd");
		LoginID currentLoginID = new LoginID(command.getLoginID());
		// check if update loginID
		if (!currentLoginID.equals(updateUser.getLoginID())) {
			// check if loginId is existed
			List<User> userList = userRepo.getByContractCode(contractCode);
			List<LoginID> loginIDs = userList.stream().map(u -> new LoginID(u.getLoginID().toString()))
					.collect(Collectors.toList());
			if (loginIDs.contains(currentLoginID)) {
				throw new BusinessException("Msg_61", currentLoginID.toString());
			}
			updateUser.setLoginID(currentLoginID);
		}
		
		if (personalId != null) {
			if((updateUser.getAssociatedPersonID().isPresent() && !updateUser.getAssociatedPersonID().get().equals(personalId)
					&& !userRepo.getByContractAndPersonalId(contractCode, personalId).isEmpty())
						|| (!updateUser.getAssociatedPersonID().isPresent() && !userRepo.getByContractAndPersonalId(contractCode, personalId).isEmpty()))
			throw new BusinessException("Msg_716");
		}
		
		// check for exitstence of system admin
		if (!registrationUserService.checkSystemAdmin(command.getUserID(), validityPeriod))
			throw new BusinessException("Msg_330");

		// check if change pass
		if (password != null) {
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
			updateUser.setPassword(hashPW);
		}
		// update user
		updateUser.setMailAddress(Optional.ofNullable(command.getMailAddress() == null ? null : new MailAddress(command.getMailAddress())));
		updateUser.setAssociatedPersonID(Optional.ofNullable(command.getAssociatedPersonID() == null ? null : command.getAssociatedPersonID()));
		updateUser.setExpirationDate(validityPeriod);
		updateUser.setSpecialUser(command.isSpecialUser() ? DisabledSegment.True : DisabledSegment.False);
		updateUser.setMultiCompanyConcurrent(command.isMultiCompanyConcurrent() ? DisabledSegment.True : DisabledSegment.False);
		updateUser.setUserName(Optional.of(new UserName(command.getUserName())));

		userRepo.update(updateUser);
	}

}
