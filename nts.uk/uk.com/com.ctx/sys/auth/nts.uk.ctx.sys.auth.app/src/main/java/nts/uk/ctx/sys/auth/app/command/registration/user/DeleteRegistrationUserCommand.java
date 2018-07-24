package nts.uk.ctx.sys.auth.app.command.registration.user;

import lombok.Data;

@Data
public class DeleteRegistrationUserCommand {
	private String userID;
	private String personalId;
}
