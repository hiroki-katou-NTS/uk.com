package nts.uk.ctx.sys.auth.ws.avatar;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


import nts.uk.ctx.sys.auth.app.command.user.information.avatar.UserAvatarDto;
import nts.uk.ctx.sys.auth.dom.avatar.AvatarRepository;
import nts.uk.ctx.sys.auth.dom.avatar.UserAvatar;
import nts.uk.shr.com.context.AppContexts;

@Path("ctx/sys/auth/user/avatar")
@Produces("application/json")
public class UserAvatarWs {

	@Inject
	AvatarRepository repo;

	@POST
	@Path("get")
	public UserAvatarDto getAvatar() {
		String personalId = AppContexts.user().personId();
		UserAvatarDto avatarDto = UserAvatarDto.builder().build();
		Optional<UserAvatar> avatar = repo.getAvatarByPersonalId(personalId);
		avatar.ifPresent(ava -> ava.setMemento(avatarDto));
		return avatarDto;
	}
}
