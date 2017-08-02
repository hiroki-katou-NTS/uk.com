package command.roles.auth;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoRoleAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoRoleAuthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdatePersonInfoRoleAuthCommandHandler extends CommandHandler<UpdatePersonInfoRoleAuthCommand> {
	@Inject
	private PersonInfoRoleAuthRepository personRoleAuthRepository;

	@Override
	public void handle(CommandHandlerContext<UpdatePersonInfoRoleAuthCommand> context) {
		UpdatePersonInfoRoleAuthCommand update = context.getCommand();
		if (update.getRoleIds().size() <= 0) {
			throw new BusinessException(new RawErrorMessage("Msg_365"));
		}
		String companyId = AppContexts.user().companyId();
		Optional<PersonInfoRoleAuth> p_RoleDestination = this.personRoleAuthRepository
				.getDetailPersonRoleAuth(update.getRoleIdDestination());
		if (p_RoleDestination.isPresent()) {
			update.getRoleIds().forEach(c -> {
				this.personRoleAuthRepository.delete(c);
				PersonInfoRoleAuth insert = PersonInfoRoleAuth.createFromJavaType(c, companyId,
						p_RoleDestination.get().getAllowDocUpload().value, p_RoleDestination.get().getAllowMapBrowse().value,
						p_RoleDestination.get().getAllowDocUpload().value, p_RoleDestination.get().getAllowDocRef().value,
						p_RoleDestination.get().getAllowAvatarUpload().value, p_RoleDestination.get().getAllowAvatarRef().value);
				this.personRoleAuthRepository.add(insert);
			});
		}

	}

}
