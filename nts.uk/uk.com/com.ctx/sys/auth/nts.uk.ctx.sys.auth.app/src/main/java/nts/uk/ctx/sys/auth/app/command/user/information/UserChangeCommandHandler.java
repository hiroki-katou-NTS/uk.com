package nts.uk.ctx.sys.auth.app.command.user.information;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.auth.app.command.user.information.user.UserDto;
import nts.uk.ctx.sys.auth.dom.adapter.checkpassword.CheckBeforeChangePassImport;
import nts.uk.ctx.sys.auth.dom.adapter.checkpassword.CheckBeforePasswordAdapter;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.dom.user.HashPassword;
import nts.uk.ctx.sys.auth.dom.user.Language;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

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
		String userId = AppContexts.user().userId();

		// cmd 1 : ユーザを変更する + cmd 2 : ログを記載する
		if (!this.isPasswordTabNull(command.getUserChange())) {
			this.userChangeHandler(command.getUserChange(), userId);
		}
		this.updateLanguage(command.getUserChange().getLanguage(), userId);
	}

	// ユーザを変更する - check is password tab is null
	private boolean isPasswordTabNull(UserDto user) {
		return (user.getCurrentPassword().trim().length() == 0 && user.getNewPassword().trim().length() == 0
				&& user.getConfirmPassword().trim().length() == 0);
	}

	//  ユーザを変更する - update language only
	private void updateLanguage(int language, String userId) {
		Optional<User> currentUser = userRepository.getByUserID(userId);
        currentUser.ifPresent(current -> {
           current.setLanguage(EnumAdaptor.valueOf(language, Language.class));
            userRepository.update(current);
        });
	}

	// ユーザを変更する
	private void userChangeHandler(UserDto user, String userId) {
		CheckBeforeChangePassImport checkBeforeChangePassImport = checkBeforePasswordAdapter.checkBeforeChangePassword(
				userId, user.getCurrentPassword(), user.getNewPassword(), user.getConfirmPassword());
		if (!checkBeforeChangePassImport.isError()) {
			Optional<User> currentUser = userRepository.getByUserID(userId);
			currentUser.ifPresent(current -> {
				String newPassHash = PasswordHash.generate(user.getNewPassword(), userId);
				HashPassword hashPW = new HashPassword(newPassHash);
				current.setPassword(hashPW);
				userRepository.update(current);
				//isWriteLog == true
				// ログを記載する
				PasswordChangeLog passwordChangeLog = new PasswordChangeLog(current.getLoginID().v(), userId, GeneralDateTime.now(), hashPW);
				passwordChangeLogRepository.add(passwordChangeLog);
			});
		} else {
			throw new BusinessException(checkBeforeChangePassImport.getMessage().get(0).getMessage());
		}
	}
}
