package nts.uk.ctx.sys.auth.dom.password.changelog;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;

@Getter
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
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PasswordChangeLogSetMemento memento) {
		memento.setLoginId(this.loginID);
		memento.setUserId(this.userID);
		memento.setModifiedDate(this.modifiedDate);
		memento.setPassword(this.password);
	}

	/**
	 * Instantiates a new password change log.
	 *
	 * @param memento the memento
	 */
	public PasswordChangeLog(PasswordChangeLogGetMemento memento) {
		this.loginID = memento.getLoginId();
		this.userID = memento.getUserId();
		this.modifiedDate = memento.getModifiedDateTime();
		this.password = memento.getPassword();
	}

}
