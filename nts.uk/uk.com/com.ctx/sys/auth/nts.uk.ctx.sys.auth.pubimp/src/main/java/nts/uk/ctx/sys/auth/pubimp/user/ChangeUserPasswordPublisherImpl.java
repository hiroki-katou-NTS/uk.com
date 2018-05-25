package nts.uk.ctx.sys.auth.pubimp.user;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.auth.dom.password.changelog.LoginId;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.dom.user.HashPassword;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.ctx.sys.auth.pub.user.ChangeUserPasswordPublisher;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ChangeUserPasswordPublisherImpl implements ChangeUserPasswordPublisher {

	@Inject
	private PasswordChangeLogRepository passwordChangeLogRepository;
	
	@Inject
	private UserRepository userRepository;

	@Override
	public void changePass(String userId, String newPassword) {
		// NPUT．パスワードをハッシュ化する
		String newPassHash = PasswordHash.generate(newPassword, userId);

		String loginId = AppContexts.user().employeeCode();

		PasswordChangeLog passwordChangeLog = new PasswordChangeLog(new LoginId(loginId), userId, GeneralDateTime.now(),
				new HashPassword(newPassHash));

		// ドメインモデル「パスワード変更ログ」を登録する
		this.passwordChangeLogRepository.add(passwordChangeLog);

		// ドメインモデル「ユーザ」を更新登録する
		User user = userRepository.getByUserID(userId).get();
		user.setPassword(new HashPassword(newPassHash));
		userRepository.update(user);
	}

}
