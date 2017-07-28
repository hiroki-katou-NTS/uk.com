package command.roles.auth;

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
	protected void handle(CommandHandlerContext<UpdatePersonInfoRoleAuthCommand> context) {
		UpdatePersonInfoRoleAuthCommand update = context.getCommand();
		if(update.getRoleIds().size() <= 0){
			throw new BusinessException(new RawErrorMessage("Msg_365"));
		}
		String companyId = AppContexts.user().companyId();
		PersonInfoRoleAuth p_RoleDestination = this.personRoleAuthRepository
				.getDetailPersonRoleAuth(update.getRoleIdDestination()).get();
		update.getRoleIds().forEach(c -> {
			this.personRoleAuthRepository.delete(c);
			PersonInfoRoleAuth insert = PersonInfoRoleAuth.createFromJavaType(c, companyId,
					p_RoleDestination.getAllowDocUpload().value, p_RoleDestination.getAllowMapBrowse().value,
					p_RoleDestination.getAllowDocUpload().value, p_RoleDestination.getAllowDocRef().value,
					p_RoleDestination.getAllowAvatarUpload().value, p_RoleDestination.getAllowAvatarRef().value);
			this.personRoleAuthRepository.update(insert);
		});
	}

}
