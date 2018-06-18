package nts.uk.ctx.pereg.app.command.roles.auth.functionauth;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthority;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthorityRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegisterFuncAuthCommandHandler extends CommandHandler<RegisterFuncAuthCommand> {

	@Inject
	private PersonInfoAuthorityRepository authRepo;

	@Override
	protected void handle(CommandHandlerContext<RegisterFuncAuthCommand> context) {
		RegisterFuncAuthCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String roleId = command.getRoleId();
		
		Map<Integer, PersonInfoAuthority> authMap = authRepo.getListOfRole(companyId, roleId);
		command.getFunctionAuthList().forEach(auth -> {
			
			PersonInfoAuthority authDomain = PersonInfoAuthority.createFromJavaType(companyId, roleId,
					auth.getFunctionNo(), auth.isAvailable());
			
			if (authMap.containsKey(auth.getFunctionNo())) {
				authRepo.add(authDomain);
			} else {
				authRepo.update(authDomain);
			}
			
		});

	}

}
