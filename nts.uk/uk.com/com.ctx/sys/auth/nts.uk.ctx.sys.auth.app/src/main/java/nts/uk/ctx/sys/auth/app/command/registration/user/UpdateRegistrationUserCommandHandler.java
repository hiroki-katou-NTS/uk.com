package nts.uk.ctx.sys.auth.app.command.registration.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
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

@Stateless
@Transactional
public class UpdateRegistrationUserCommandHandler
		extends CommandHandlerWithResult<UpdateRegistrationUserCommand, String> {

	@Inject
	private UserRepository userRepo;

	@Inject
	private RegistrationUserService registrationUserService;

	@Inject
	private PasswordChangeLogRepository passwordChangeLogRepository;

	@Override
	protected String handle(CommandHandlerContext<UpdateRegistrationUserCommand> context) {
		UpdateRegistrationUserCommand command = context.getCommand();
		String userId = command.getUserID();
		User updateUser = userRepo.getByUserID(userId).get();
		String contractCode = updateUser.getContractCode().toString();
		String password = command.getPassword();
		GeneralDate validityPeriod = command.getValidityPeriod();
		// check if loginId is existed
		List<User> userList = userRepo.getByContractCode(contractCode);
		List<LoginID> loginIDs = userList.stream().map(u -> new LoginID(u.getLoginID().toString()))
				.collect(Collectors.toList());
		LoginID currentLoginID = new LoginID(command.getLoginID());
		if (loginIDs.contains(currentLoginID)) {
			throw new BusinessException("Msg_61");
		}
		// check for exitstence of system admin
		if (!registrationUserService.checkSystemAdmin(command.getUserID(), validityPeriod))
			throw new BusinessException("Msg_330");

		// password policy check
		if (!registrationUserService.checkPasswordPolicy(userId, password, contractCode).isError())
			throw new BusinessException("Msg_320");
		HashPassword hashPW = new HashPassword(password);

		// register password change log
		// get domain PasswordChangeLog
		PasswordChangeLog passwordChangeLog = new PasswordChangeLog(currentLoginID.toString(), userId,
				GeneralDateTime.now(), hashPW);
		passwordChangeLogRepository.add(passwordChangeLog);
		// update user
		updateUser.setLoginID(new LoginID(command.getLoginID()));
		updateUser.setMailAddress(Optional.of(new MailAddress(command.getMailAddress())));
		updateUser.setAssociatedPersonID(Optional.of(command.getAssociatedPersonID()));
		updateUser.setExpirationDate(validityPeriod);
		updateUser.setSpecialUser(DisabledSegment.valueOf(String.valueOf(command.isSpecialUser())));
		updateUser.setMultiCompanyConcurrent(DisabledSegment.valueOf(String.valueOf(command.isMultiCompanyConcurrent())));
		updateUser.setUserName(Optional.of(new UserName(command.getUserName())));
		
		userRepo.update(updateUser);
		return userId;
	}

}
