package nts.uk.ctx.pereg.app.command.roles.auth.functionauth;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

		List<RegisterFuncAuthCommandParam> params = command.getFunctionAuthList().stream().map(
				auth -> new RegisterFuncAuthCommandParam(companyId, roleId, auth.getFunctionNo(), auth.isAvailable()))
				.collect(Collectors.toList());

		params.forEach(param -> {
			PersonInfoAuthority authDomain = new PersonInfoAuthority(param);
			if (authMap.containsKey(param.functionNo())) {
				authRepo.update(authDomain);
			} else {
				authRepo.add(authDomain);
			}
		});

	}

}
