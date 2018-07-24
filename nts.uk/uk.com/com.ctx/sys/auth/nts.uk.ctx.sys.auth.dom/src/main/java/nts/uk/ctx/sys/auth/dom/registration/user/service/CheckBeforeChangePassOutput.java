package nts.uk.ctx.sys.auth.dom.registration.user.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.auth.dom.registration.user.service.RegistrationUserServiceImp.PasswordMessageObject;

@AllArgsConstructor
@Data
public class CheckBeforeChangePassOutput {
	private boolean error;
	private List<PasswordMessageObject> message;
}
