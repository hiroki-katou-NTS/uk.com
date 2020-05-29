package nts.uk.ctx.at.auth.dom.adapter.user;

import java.util.Optional;

public interface UserAuthAdapter {

	Optional<String> getUserIDByEmpID(String employeeID);
}
