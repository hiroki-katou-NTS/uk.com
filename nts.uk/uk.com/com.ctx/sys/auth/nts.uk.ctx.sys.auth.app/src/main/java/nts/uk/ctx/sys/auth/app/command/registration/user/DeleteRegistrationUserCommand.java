package nts.uk.ctx.sys.auth.app.command.registration.user;

import lombok.Data;
/**
 * Instantiates a new delete registration user command.
 */
@Data
public class DeleteRegistrationUserCommand {
	
	//ユーザID
	/** The user ID. */
	private String userID;
	//個人ID
	/** The personal id. */
	private String personalId;
}
