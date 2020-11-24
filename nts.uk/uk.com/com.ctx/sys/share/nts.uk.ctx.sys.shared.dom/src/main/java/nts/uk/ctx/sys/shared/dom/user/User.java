package nts.uk.ctx.sys.shared.dom.user;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.security.hash.password.PasswordHash;
import nts.gul.security.hash.password.PasswordHash.Verifier;
import nts.gul.text.IdentifierUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.shared.dom.user.password.HashPassword;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

@Getter
@Setter
@AllArgsConstructor
/**
 * ユーザ User
 */
public class User extends AggregateRoot {

	// ID
	/** The user id. */
	private String userID;
	// デフォルトユーザ
	private boolean defaultUser;
	// パスワード
	/** The password. */
	private HashPassword password;
	// ログインID
	/** The login id. */
	private LoginID loginID;
	// 契約コード
	/** The contract code. */
	private ContractCode contractCode;
	// 有効期限
	/** The expiration date. */
	private GeneralDate expirationDate;
	// 特別利用者
	/** The special user. */
	private DisabledSegment specialUser;
	// 複数会社を兼務する
	/** The multi company concurrent. */
	private DisabledSegment multiCompanyConcurrent;
	// メールアドレス
	/** The mail address. */
	private Optional<MailAddress> mailAddress;

	// ユーザ名
	/** The user name. */
	private Optional<UserName> userName;

	// 紐付け先個人ID
	/** The associated employee id. */
	private Optional<String> associatedPersonID;
	@Getter
	// パスワード状態
	/** PasswordStatus **/
	private PassStatus passStatus;
	
	/**
	 * ビルトインユーザを作る
	 * @param contractCode
	 * @param loginID
	 * @param passwordPlainText
	 * @return
	 */
	public static User createBuiltInUser(String contractCode, String loginID, String passwordPlainText) {
		
		String userId = IdentifierUtil.randomUniqueId();
		
		return new User(
				userId,
				true, // defaultUser
				new HashPassword(PasswordHash.generate(passwordPlainText, userId)),
				new LoginID(loginID),
				new ContractCode(contractCode),
				GeneralDate.max(), // expirationDate
				DisabledSegment.True, // specialUser
				DisabledSegment.False, // multiCompanyConcurrent
				Optional.empty(), // mailAddress
				Optional.of(new UserName("system")), // userName
				Optional.empty(), // associatedPersonID
				PassStatus.Official);
	}

	public static User createFromJavatype(String userID, Boolean defaultUser, String password, String loginID,
			String contractCode, GeneralDate expirationDate, int specialUser, int multiCompanyConcurrent,
			String mailAddress, String userName, String associatedPersonID, int passStatus) {

		return new User(userID, defaultUser, new HashPassword(password), new LoginID(loginID.trim()),
				new ContractCode(contractCode), expirationDate, EnumAdaptor.valueOf(specialUser, DisabledSegment.class),
				EnumAdaptor.valueOf(multiCompanyConcurrent, DisabledSegment.class),
				Optional.ofNullable(mailAddress == null ? null : new MailAddress(mailAddress)),
				Optional.ofNullable(userName == null ? null : new UserName(userName)),
				Optional.ofNullable(associatedPersonID == null ? null : associatedPersonID),
				EnumAdaptor.valueOf(passStatus, PassStatus.class));
	}

	public boolean hasAssociatedPersonID() {
		return !StringUtil.isNullOrEmpty(this.associatedPersonID.get(), false);
	}
	
	public boolean isCorrectPassword(String password) {
		Verifier salt = PasswordHash.verifyThat(password, this.getUserID());
		return salt.isEqualTo(this.getPassword().toString());
	}
}
