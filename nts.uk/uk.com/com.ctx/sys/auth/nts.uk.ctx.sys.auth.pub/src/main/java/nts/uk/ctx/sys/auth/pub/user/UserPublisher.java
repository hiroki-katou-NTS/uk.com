package nts.uk.ctx.sys.auth.pub.user;

import java.util.Optional;

public interface UserPublisher {

	Optional<UserDto> getUserInfo(String userId);
}
