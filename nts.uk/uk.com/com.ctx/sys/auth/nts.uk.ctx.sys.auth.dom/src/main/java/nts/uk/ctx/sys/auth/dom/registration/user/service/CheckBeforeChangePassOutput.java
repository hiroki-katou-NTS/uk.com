package nts.uk.ctx.sys.auth.dom.registration.user.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.auth.dom.registration.user.service.RegistrationUserServiceImp.PasswordMessageObject;

/**
 * Instantiates a new check before change pass output.
 *
 * @param error the error
 * @param message the message
 */
@AllArgsConstructor

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Data
public class CheckBeforeChangePassOutput {
	
	/** The error. */
	private boolean error;
	
	/** The message. */
	private List<PasswordMessageObject> message;
}
