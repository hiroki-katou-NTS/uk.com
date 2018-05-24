package nts.uk.ctx.sys.auth.dom.password.changelog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.auth.dom.user.HashPassword;

@Getter
@Setter
@AllArgsConstructor
public class PasswordChangeLog extends AggregateRoot{
	
	/** The login ID. */
	//ログID
	private LoginId loginID;
	
	/** The user ID. */
	//ユーザID
	private String userID;
	
	/** The modified date. */
	//変更日時
	private GeneralDateTime modifiedDate;
	
	/** The password. */
	//パスワード
	private HashPassword password;
	
	/**
	 * Creates the from javatype.
	 *
	 * @param loginId the login ID
	 * @param userID the user ID
	 * @param modifiedDate the modified date
	 * @param password the password
	 * @return the password change log
	 */
	public static PasswordChangeLog createFromJavatype(String loginID, String userID, GeneralDateTime modifiedDate, String password) {

		return new PasswordChangeLog(new LoginId(loginID), userID, modifiedDate, new HashPassword(password));
	}

}
