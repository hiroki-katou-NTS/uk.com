package nts.uk.ctx.sys.auth.dom.user;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;

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
	 * 言語
	 */
	private Language language;

	public static User createFromJavatype(String userID, Boolean defaultUser, String password, String loginID,
			String contractCode, GeneralDate expirationDate, int specialUser, int multiCompanyConcurrent,
			String mailAddress, String userName, String associatedPersonID, int passStatus, int language) {

		return new User(userID, defaultUser, new HashPassword(password), new LoginID(loginID.trim()),
				new ContractCode(contractCode), expirationDate, EnumAdaptor.valueOf(specialUser, DisabledSegment.class),
				EnumAdaptor.valueOf(multiCompanyConcurrent, DisabledSegment.class),
				Optional.ofNullable(mailAddress == null ? null : new MailAddress(mailAddress)),
				Optional.ofNullable(userName == null ? null : new UserName(userName)),
				Optional.ofNullable(associatedPersonID == null ? null : associatedPersonID),
				EnumAdaptor.valueOf(passStatus, PassStatus.class),
				EnumAdaptor.valueOf(language, Language.class));
	}

	public boolean hasAssociatedPersonID() {
		return !StringUtil.isNullOrEmpty(this.associatedPersonID.get(), false);
	}

}
