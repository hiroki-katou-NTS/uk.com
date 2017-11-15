package nts.uk.ctx.sys.auth.app.command.person.role;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.PersonRoleRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleAtr;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSetRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemovePersonRoleCommandHandler extends CommandHandler<RemovePersonRoleCommand> {
	@Inject
	private RoleIndividualGrantRepository roleGrantRepo;
	@Inject
	private RoleRepository roleRepo;
	@Inject
	private DefaultRoleSetRepository defaultRoleSetRepo;
	@Inject
	private RoleSetRepository roleSetRepo;
	@Inject
	private PersonRoleRepository personRoleRepo;

	@Override
	protected void handle(CommandHandlerContext<RemovePersonRoleCommand> context) {
		final RemovePersonRoleCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		if (command.getAssignAtr() == RoleAtr.INCHARGE.value) {
			List<RoleIndividualGrant> roleIndi = roleGrantRepo.findByRoleId(command.getRoleId());
			if (!roleIndi.isEmpty())
				throw new BusinessException("Msg_584");
			else
				roleRepo.remove(command.getRoleId());
				personRoleRepo.remove(command.getRoleId());

		} else {
			Optional<DefaultRoleSet> defaultOpt = defaultRoleSetRepo.findByCompanyId(companyId);
			if (!defaultOpt.isPresent()) {
				DefaultRoleSet defaultRoleSet = defaultOpt.get();
				Optional<RoleSet> roleSetOpt = roleSetRepo
						.findByRoleSetCdAndCompanyId(defaultRoleSet.getRoleSetCd().toString(), companyId);
				if (roleSetOpt.isPresent() && roleSetOpt.get().getPersonInfRole().get().equals(command.getRoleId()))
					throw new BusinessException("Msg_586");
			} else
				roleRepo.remove(command.getRoleId());
				personRoleRepo.remove(command.getRoleId());
		}
		
		

	}

}
