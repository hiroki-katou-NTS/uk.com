package nts.uk.screen.com.app.find.user.information.password.changelog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.LoginPasswordOfUser;

/**
 * Dto パスワード変更ログ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeLogDto {
	
	/**
	 * ログID
	 */
	private String logId;
	
	/**
	 * ユーザID
	 */
	private String userId;
	
	/**
	 * 変更日時
	 */
	private GeneralDateTime modifiedDate;
	
	/**
	 * パスワード
	 */
	private String password;
	
	public static PasswordChangeLogDto toDto(LoginPasswordOfUser domain) {
		return domain == null ? null : new PasswordChangeLogDto(
				null,
				domain.getUserId(),
				domain.getLatestPassword().isPresent() ? domain.getLatestPassword().get().getChangedDateTime() : null,
				domain.getLatestPassword().isPresent() ? domain.getLatestPassword().get().getHashedPassword().toString() : null
				);
	}
}
