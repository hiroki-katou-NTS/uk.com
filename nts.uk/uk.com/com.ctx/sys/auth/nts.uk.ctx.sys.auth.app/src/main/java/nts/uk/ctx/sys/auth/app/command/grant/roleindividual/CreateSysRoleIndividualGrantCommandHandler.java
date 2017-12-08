package nts.uk.ctx.sys.auth.app.command.grant.roleindividual;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class CreateSysRoleIndividualGrantCommandHandler extends CommandHandlerWithResult<CreateRoleIndividualGrantCommand, CreateRoleIndividualGrantCommandResult> {

	@Inject
	private RoleRepository roleRepository;
	
	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepo;

	@Inject
	private UserRepository userRepo;

	private final String COMPANY_ID_SYSADMIN = "00000000000000000";

	@Override
	protected CreateRoleIndividualGrantCommandResult handle(CommandHandlerContext<CreateRoleIndividualGrantCommand> context) {
		CreateRoleIndividualGrantCommand command = context.getCommand();
				
		if (StringUtil.isNullOrEmpty(command.getUserID(), true)) {
			throw new BusinessException("Msg_218", "CAS012_17");
		}
		
		if (command.getRoleType() != RoleType.COMPANY_MANAGER.value)
			command.setCompanyID(COMPANY_ID_SYSADMIN);
		Optional<RoleIndividualGrant> roleIndividualGrant = roleIndividualGrantRepo.findByUserCompanyRoleType(command.getUserID(), command.getCompanyID(), command.getRoleType());
		if (roleIndividualGrant.isPresent()) {
			throw new BusinessException("Msg_3");
		}
		String contractCD = AppContexts.user().contractCode();
		Optional<Role> uniqueRole = roleRepository.findByContractCDRoleTypeAndCompanyID(contractCD, command.getRoleType(),command.companyID);
		
		
		// ドメインモデル「ロール個人別付与」を新規登録する | Register a domain model "Role individual grant"
		RoleIndividualGrant domain = command.toDomain(uniqueRole.get().getRoleId());
		roleIndividualGrantRepo.add(domain);

		if (command.isSetRoleAdminFlag() == true) {
			RoleIndividualGrant roleIndiGrantSys = RoleIndividualGrant.createFromJavaType(
					command.getUserID(),
					uniqueRole.get().getRoleId(),
					command.getDecisionCompanyID(),
					command.getRoleType(),
					command.getStartValidPeriod(),
					command.getEndValidPeriod());
			// ドメインモデル「ロール個人別付与」を新規登録する | Register a domain model "Role individual grant"
			roleIndividualGrantRepo.add(roleIndiGrantSys);
		}
		
		Optional<User> user = userRepo.getByUserID(command.getUserID());
		if (user.get().isDefaultUser() == true) {
			user.get().setExpirationDate(command.getEndValidPeriod());
		}
		
		return new CreateRoleIndividualGrantCommandResult(domain.getCompanyId(), domain.getUserId(), domain.getRoleType().value);
	}
}
