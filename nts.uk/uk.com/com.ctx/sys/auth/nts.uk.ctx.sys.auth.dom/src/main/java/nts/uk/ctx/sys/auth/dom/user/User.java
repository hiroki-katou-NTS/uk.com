package nts.uk.ctx.sys.auth.dom.user;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
/** ユーザ
 * User
 *  */
public class User extends AggregateRoot {
	
	//ID
	/** The user id. */
	private String userID;
    //デフォルトユーザ
	private boolean defaultUser; 
	//パスワード
	/** The password. */
	private HashPassword password;

	//ログインID
	/** The login id. */
	private LoginID loginID;

	//契約コード
	/** The contract code. */
	private ContractCode contractCode;

	//有効期限
	/** The expiration date. */
	private GeneralDate expirationDate;

	//特別利用者
	/** The special user. */
	private boolean specialUser;

	//複数会社を兼務する
	/** The multi company concurrent. */
	private boolean multiCompanyConcurrent;

	//メールアドレス
	/** The mail address. */
	private MailAddress mailAddress;

	//ユーザ名
	/** The user name. */
	private UserName userName;

	//紐付け先個人ID
	/** The associated employee id. */
	private String associatedPersonID;
	
	public static User createFromJavatype(String userID,Boolean defaultUser,String password, String loginID ,String contractCode,GeneralDate expirationDate, boolean specialUser ,boolean multiCompanyConcurrent ,String mailAddress ,String userName,String associatedPersonID  ){
		
		return new User(userID,
				defaultUser,
				new HashPassword(password),
				new LoginID(loginID),
				new ContractCode(contractCode),
				expirationDate,
				specialUser,
				multiCompanyConcurrent,
				new MailAddress(mailAddress) ,
			    new UserName(userName),
				associatedPersonID
				);
	}

}
