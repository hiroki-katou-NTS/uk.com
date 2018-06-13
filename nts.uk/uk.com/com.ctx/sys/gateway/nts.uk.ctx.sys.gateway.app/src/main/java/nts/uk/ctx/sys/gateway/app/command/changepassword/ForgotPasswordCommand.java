package nts.uk.ctx.sys.gateway.app.command.changepassword;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordCommand {
	
	/** The url. */
	private String url;
	
	/** The old password. */
	private String oldPassword;

	/** The new password. */
	private String newPassword;

	/** The confirm new password. */
	private String confirmNewPassword;
}
