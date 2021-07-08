package nts.uk.ctx.sys.auth.app.command.user.information;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.app.command.user.information.user.UserDto;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * アカウント情報を登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UserChangeCommandHandler extends CommandHandler<UserChangeCommand> {
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private CheckBeforePasswordAdapter checkBeforePasswordAdapter;
	
	@Inject
	private PasswordChangeLogRepository passwordChangeLogRepository;
	
	@Override
	protected void handle(CommandHandlerContext<UserChangeCommand> commandHandlerContext) {
		
		UserChangeCommand command = commandHandlerContext.getCommand();
		//#113902
		boolean isUseOfPassword = command.getUseOfPassword();
		String userId = AppContexts.user().userId();
		
		// cmd 1 : ユーザを変更する + cmd 2 : ログを記載する
		if (!this.isPasswordTabNull(command.getUserChange()) && isUseOfPassword) {
			this.userChangeHandler(command.getUserChange(), userId);
		}
	}
	
	// ユーザを変更する - check is password tab is null
	private boolean isPasswordTabNull(UserDto user) {
		return (user.getCurrentPassword().trim().length() == 0 && user.getNewPassword().trim().length() == 0
				&& user.getConfirmPassword().trim().length() == 0);
	}
	
	// ユーザを変更する
	private void userChangeHandler(UserDto user, String userId) {
		
//		val atomTask = ChangeLoginPasswordOfUser.change(
//				require, 
//				command.getUserId(), 
//				command.getNewPassword(), 
//				command.getConfirmNewPassword());
		
		
//		CheckBeforeChangePassImport checkBeforeChangePassImport = checkBeforePasswordAdapter.checkBeforeChangePassword(
//				userId, user.getCurrentPassword(), user.getNewPassword(), user.getConfirmPassword());
//		if (!checkBeforeChangePassImport.isError()) {
//			Optional<User> currentUser = userRepository.getByUserID(userId);
//			currentUser.ifPresent(current -> {
//				String newPassHash = PasswordHash.generate(user.getNewPassword(), userId);
//				HashPassword hashPW = new HashPassword(newPassHash);
//				//current.setPassword(hashPW);
//				userRepository.update(current);
//				//isWriteLog == true
//				// ログを記載する
//				PasswordChangeLog passwordChangeLog = new PasswordChangeLog(current.getLoginID().v(), userId, GeneralDateTime.now(), hashPW);
//				//passwordChangeLogRepository.add(passwordChangeLog);
//			});
//		} else {
//			List<String> errorMessages = checkBeforeChangePassImport.getMessage().stream()
//				.map(msg -> msg.getErrorMessage())
//				.collect(Collectors.toList());
//			throw new BusinessException(new RawErrorMessage(String.join("\r\n", errorMessages)));
//		}
	}
}
