package nts.uk.ctx.sys.auth.pubimp.user;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.ctx.sys.auth.pub.user.UserDto;
import nts.uk.ctx.sys.auth.pub.user.UserPublisher;

@Stateless
public class UserPublisherImpl implements UserPublisher  {

	@Inject
	private UserRepository userRepo;
	
	@Override
	public Optional<UserDto> getUserInfo(String userId) {
		return Optional.ofNullable(toDto(userRepo.getByUserID(userId).orElse(null)));
	}
	
	private UserDto toDto(User user) {
		return user != null 
				? new UserDto(user.getUserID(), user.getUserName().v(), user.getAssociatedPersonID()) 
				: null;
	}

}
