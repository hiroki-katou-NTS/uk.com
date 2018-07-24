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
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.auth.dom.adapter.securitypolicy.PasswordPolicyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.securitypolicy.PasswordPolicyImport;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.dom.user.HashPassword;
import nts.uk.ctx.sys.auth.dom.user.LoginID;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;

@Stateless
@Transactional
public class AddRegistrationUserCommandHandler extends CommandHandlerWithResult<AddRegistrationUserCommand, String> {
	
	@Inject UserRepository userRepo;
	
	@Inject PasswordPolicyAdapter passwordPolicyAdapter;
	
	@Inject PasswordChangeLogRepository passwordChangeLogRepository;

	@Override
	protected String handle(CommandHandlerContext<AddRegistrationUserCommand> context) {
		AddRegistrationUserCommand command = context.getCommand();
		String contractCode = command.getContractCode();
		String personalId = command.getAssociatedPersonID();
		String password = command.getPassword();
		String userId = IdentifierUtil.randomUniqueId();
		// check if loginId is existed
		List<User> userList = userRepo.getByContractCode(contractCode);
		List<LoginID> loginIDs = userList.stream().map(u -> new LoginID(u.getLoginID().toString())).collect(Collectors.toList());
		LoginID currentLoginID = new LoginID(command.getLoginID());
		if(loginIDs.contains(currentLoginID)) {
			throw new BusinessException("Msg_61");
		}
		
		if (personalId != null && !userRepo.getByContractAndPersonalId(contractCode, personalId).isEmpty()) {
			throw new BusinessException("Msg_716");
		}
		// get password policy model
		Optional<PasswordPolicyImport> passIP = passwordPolicyAdapter.getPasswordPolicy(contractCode);
		// password policy check
		if(!this.checkPasswordPolicy(passIP.get(), password))
			throw new BusinessException("Msg_320");
		HashPassword hashPW = new HashPassword(password);
		
		// register password change log
		//get domain PasswordChangeLog
		PasswordChangeLog passwordChangeLog = new PasswordChangeLog(currentLoginID.toString(), userId, GeneralDateTime.now(), hashPW);
		passwordChangeLogRepository.add(passwordChangeLog);
		// register user
		User newUser = User.createFromJavatype(userId, false, hashPW.toString(), command.getLoginID(),
				contractCode, command.getExpirationDate(), 0, 0, command.getMailAddress(),
				command.getUserName(), command.getAssociatedPersonID(), 1);
		userRepo.addNewUser(newUser);

		return userId;
	}

	private boolean checkPasswordPolicy(PasswordPolicyImport passIP, String password) {
		// check usage classification
		if(!passIP.isUse())
			return false;
		// check degit number
		return true;
	}

}
