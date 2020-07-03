package nts.uk.ctx.at.auth.ac;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.auth.dom.adapter.user.UserAuthAdapter;
import nts.uk.ctx.sys.auth.pub.user.UserPublisher;

@Stateless
public class UserAuthAdapterImpl implements UserAuthAdapter {
	
	@Inject
	private UserPublisher userPublisher;
	
	@Override
	public Optional<String> getUserIDByEmpID(String employeeID) {
		return userPublisher.getUserIDByEmpID(employeeID);
	}
}
