package nts.uk.ctx.sys.auth.app.command.registration.user;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class UpdateRegistrationUserCommand {
	// ãƒ­ã‚°ã‚¤ãƒ³ID
	/** The login id. */
	private String loginID;
	private String userID;
	// —LŒøŠúŠÔ
	private GeneralDate validityPeriod;
	/** The password. */
	private String password;
}
