package nts.uk.ctx.sys.auth.app.command.registration.user;

import java.text.DateFormat;
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
import nts.uk.ctx.sys.auth.dom.adapter.securitypolicy.PasswordPolicyImport;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.grant.service.RoleIndividualService;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.user.HashPassword;
import nts.uk.ctx.sys.auth.dom.user.LoginID;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateRegistrationUserCommandHandler extends CommandHandlerWithResult<UpdateRegistrationUserCommand, String> {
	
	@Inject UserRepository userRepo;
	
	@Inject RoleIndividualGrantRepository roleIndividualGrantRepository;
	
	@Inject RoleIndividualService roleIndividualService;
	
	@Inject PasswordChangeLogRepository passwordChangeLogRepository;
	
	private final GeneralDate LAST_DATE = GeneralDate.fromString("9999/12/31", "yyyy/MM/dd");

	@Override
	protected String handle(CommandHandlerContext<UpdateRegistrationUserCommand> context) {
		String contractCode = AppContexts.user().contractCode();
		UpdateRegistrationUserCommand command = context.getCommand();
		String userId = command.getUserID();
		String password = command.getPassword();
		GeneralDate validityPeriod = command.getValidityPeriod();
		// check if loginId is existed
		List<User> userList = userRepo.getByContractCode(contractCode);
		List<LoginID> loginIDs = userList.stream().map(u -> new LoginID(u.getLoginID().toString())).collect(Collectors.toList());
		LoginID currentLoginID = new LoginID(command.getLoginID());
		if(loginIDs.contains(currentLoginID)) {
			throw new BusinessException("Msg_61");
		}
		// check for exitstence of system admin
//		if(!roleIndividualService.checkSysAdmin(command.getUserID(), command.getValidityPeriod()))
//			throw new BusinessException("Msg_330");
//		
//		// get password policy model
//		Optional<PasswordPolicyImport> passIP = passwordPolicyAdapter.getPasswordPolicy(contractCode);
//		// password policy check
//		if(!this.checkPasswordPolicy(passIP.get(), password))
//			throw new BusinessException("Msg_320");
//		HashPassword hashPW = new HashPassword(password);
//		
//		// register password change log
//		//get domain PasswordChangeLog
//		PasswordChangeLog passwordChangeLog = new PasswordChangeLog(currentLoginID.toString(), userId, GeneralDateTime.now(), hashPW);
//		passwordChangeLogRepository.add(passwordChangeLog);
//		// register user
//		User updateUser = User.createFromJavatype(userId, false, hashPW.toString(), command.getLoginID(),
//				contractCode, command.getExpirationDate(), 0, 0, command.getMailAddress(),
//				command.getUserName(), command.getAssociatedPersonID(), 1);
//		userRepo.update(updateUser);
		return null;
	}
	
//	private boolean checkSystemAdmin(GeneralDate validityPeriod, String userId) {
//		if(validityPeriod.equals(LAST_DATE))
//			return true;
//		List<RoleIndividualGrant> roleIndiGrant = roleIndividualGrantRepository.findByUserAndRole(userId, RoleType.SYSTEM_MANAGER.value);
//		if(roleIndiGrant.isEmpty())
//			return true;
//		
//	}

}
