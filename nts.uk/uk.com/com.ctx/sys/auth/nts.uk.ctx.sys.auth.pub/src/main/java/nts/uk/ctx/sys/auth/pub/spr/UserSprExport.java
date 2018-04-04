package nts.uk.ctx.sys.auth.pub.spr;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * ユーザ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class UserSprExport {
	// ID
	/** The user id. */
	private String userID;
	
	// ログインID
	/** The login id. */
	private String loginID;
	
	// ユーザ名
	/** The user name. */
	private String userName;
	
	// 紐付け先個人ID
	/** The associated employee id. */
	private String associatedPersonID;
	
	// メールアドレス
	/** The mail address. */
	private String mailAddress;
	
	// パスワード
	/** The password. */
	private String password;
}
