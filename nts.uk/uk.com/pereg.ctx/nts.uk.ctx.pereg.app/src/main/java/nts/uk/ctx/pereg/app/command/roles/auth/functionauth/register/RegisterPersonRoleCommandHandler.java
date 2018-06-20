package nts.uk.ctx.pereg.app.command.roles.auth.functionauth.register;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthority;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthorityRepository;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleService;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRole;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRoleRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RegisterPersonRoleCommandHandler extends CommandHandler<RegisterPersonRoleCommand> {

	@Inject
	private PersonRoleRepository personRoleRepo;

	@Inject
	private RoleService roleService;

	@Inject
	private PersonInfoAuthorityRepository authRepo;

	@Override
	protected void handle(CommandHandlerContext<RegisterPersonRoleCommand> context) {
		final RegisterPersonRoleCommand command = context.getCommand();

		if (command.getCreateMode()) {
			insertPersonInfoRole(command);
		} else {
			updatePersonInfoRole(command);
		}

		// 個人情報の権限
		saveFunctionAuth(command);

	}

	private void insertPersonInfoRole(RegisterPersonRoleCommand command) {
		Role role = command.toDomain(AppContexts.user().companyId(), AppContexts.user().contractCode());
		roleService.insertRole(role);

		PersonRole personRole = new PersonRole();
		personRole.setRoleId(role.getRoleId());
		personRole.setReferFutureDate(command.getReferFutureDate());
		personRoleRepo.insert(personRole);
	}

	private void updatePersonInfoRole(RegisterPersonRoleCommand command) {
		Role role = command.toDomain(AppContexts.user().companyId(), AppContexts.user().contractCode());
		roleService.updateRole(role);

		PersonRole personRole = new PersonRole();
		personRole.setRoleId(role.getRoleId());
		personRole.setReferFutureDate(command.getReferFutureDate());
		personRoleRepo.update(personRole);
	}

	private void saveFunctionAuth(RegisterPersonRoleCommand command) {
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
