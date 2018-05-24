package nts.uk.ctx.sys.auth.dom.password.changelog;

import nts.arc.time.GeneralDateTime;

/**
 * The Interface PasswordChangeLogGetMemento.
 */
public interface PasswordChangeLogGetMemento {

	/**
	 * Gets the login id.
	 *
	 * @return the login id
	 */
	public LoginId getLoginId();
	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId();
	
	/**
	 * Gets the modified date time.
	 *
	 * @return the modified date time
	 */
	public GeneralDateTime getModifiedDateTime();
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public HashPassword getPassword();
	
}
