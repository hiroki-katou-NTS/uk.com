package nts.uk.ctx.sys.portal.ac.find.user;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.pub.user.UserPublisher;
import nts.uk.ctx.sys.portal.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.portal.dom.adapter.user.UserDto;

@Stateless
public class UserPortalAdapterImpl implements UserAdapter {
	
	@Inject
	private UserPublisher userPublisher;
	
	@Override
	public Optional<UserDto> getUserInfo(String userId) {
		return userPublisher.getUserInfo(userId).map(
				u -> new UserDto(u.getUserID(), u.getUserName(), u.getAssociatedPersonID()));
	}
}
