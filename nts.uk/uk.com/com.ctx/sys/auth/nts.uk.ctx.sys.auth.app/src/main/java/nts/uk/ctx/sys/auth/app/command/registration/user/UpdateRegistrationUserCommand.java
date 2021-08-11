package nts.uk.ctx.sys.auth.app.command.registration.user;

import lombok.Data;

/**
 * Instantiates a new update registration user command.
 */
@Data
public class UpdateRegistrationUserCommand {
	
	//ユーザID
	/** The user ID. */
	private String userID;
	//ログインID
	/** The login id. */
	private String loginID;
	//パスワード
	/** The password. */
	private String password;
	//有効期間
	/** The expiration date. */
	private String expirationDate;
	//特別利用者
	/** The special user. */
	private boolean specialUser;
	//複数会社を兼務する	
	/** The multi company concurrent. */
	private boolean multiCompanyConcurrent;
	//ユーザ名
	/** The user name. */
	private String userName;
	//メールアドレス
	/** The mail address. */
	private String mailAddress;
	//個人ID	
	/** The associated employee id. */
	private String associatedPersonID;
}
