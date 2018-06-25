package nts.uk.ctx.sys.gateway.app.command.login.dto;

/**
 * The Class LoginInforDto.
 */
public class LoginInforDto {
	
	/** The login id. */
	public String loginId;
	
	/** The user name. */
	public String userName;
	
	public String userId;
	
	/**
	 * Instantiates a new login infor dto.
	 *
	 * @param loginId the login id
	 * @param userName the user name
	 */
	public LoginInforDto(String loginId, String userName, String userId) {
		this.loginId = loginId;
		this.userName = userName;
		this.userId = userId;
	}
}
