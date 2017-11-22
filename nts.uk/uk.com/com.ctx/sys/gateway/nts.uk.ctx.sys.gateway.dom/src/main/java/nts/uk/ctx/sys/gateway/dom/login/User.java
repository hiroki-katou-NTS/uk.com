/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * The Class User.
 */
@Getter
@AllArgsConstructor
public class User {

	//ID
	/** The user id. */
	private String userId;

	//パスワード
	/** The password. */
	private HashPassword password;

	//ログインID
	/** The login id. */
	private LoginId loginId;

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
	private String associatedPersonId;

	/**
	 * Instantiates a new user.
	 *
	 * @param memento the memento
	 */
	public User(UserGetMemento memento) {
		this.userId = memento.getUserId();
		this.password = memento.getPassword();
		this.loginId = memento.getLoginId();
		this.contractCode = memento.getContractCode();
		this.expirationDate = memento.getExpirationDate();
		this.specialUser = memento.isSpecialUser();
		this.multiCompanyConcurrent = memento.isMultiCompanyConcurrent();
		this.mailAddress = memento.getMailAddress();
		this.userName = memento.getUserName();
		this.associatedPersonId = memento.getAssociatedPersonId();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(UserSetMemento memento) {
		memento.setUserId(this.userId);
		memento.setPassword(this.password);
		memento.setLoginId(this.loginId);
		memento.setContractCode(this.contractCode);
		memento.setExpirationDate(this.expirationDate);
		memento.setSpecialUser(this.specialUser);
		memento.setMultiCompanyConcurrent(this.multiCompanyConcurrent);
		memento.setMailAddress(this.mailAddress);
		memento.setUserName(this.userName);
		memento.setAssociatedPersonId(this.associatedPersonId);
	}
	
	
	/**
	 * create a User from java type
	 * @param userId
	 * @param passWord
	 * @param loginId
	 * @param contractCode
	 * @param expirationDate
	 * @param specialUser
	 * @param multiCompanyConcurrent
	 * @param mailAddress
	 * @param userName
	 * @param associatedPersonId
	 * @return User
	 */
	public static User createFromJavaType(String userId , String passWord ,String loginId , String contractCode ,
			GeneralDate expirationDate , boolean specialUser ,boolean multiCompanyConcurrent,
			String mailAddress ,  String userName ,String associatedPersonId) {
		return new User(userId,new HashPassword(passWord), new LoginId(loginId), new ContractCode(contractCode), 
				expirationDate, specialUser, multiCompanyConcurrent,
				new MailAddress(mailAddress), new UserName(userName), associatedPersonId);

	}
}
