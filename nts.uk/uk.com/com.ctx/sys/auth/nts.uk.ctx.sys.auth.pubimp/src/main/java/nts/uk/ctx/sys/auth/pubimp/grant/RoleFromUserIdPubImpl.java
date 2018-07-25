package nts.uk.ctx.sys.auth.pubimp.grant;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.service.RoleSetService;
import nts.uk.ctx.sys.auth.pub.grant.RoleFromUserIdPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RoleFromUserIdPubImpl implements RoleFromUserIdPub{

	@Inject
	private RoleIndividualGrantRepository roleIndRepo;
	
	@Inject
	private RoleSetService rolesetService;
	
	@Override
	public String getRoleFromUserId(String userId, int roleType, GeneralDate baseDate) {
		String companyId = AppContexts.user().companyId();
		if (roleType == RoleType.SYSTEM_MANAGER.value || roleType == RoleType.GROUP_COMAPNY_MANAGER.value)
			companyId = "000000000000-0000";
		
		Optional<RoleIndividualGrant> roleIndOpt = roleIndRepo.findByUserCompanyRoleTypeDate(userId, companyId, roleType, baseDate);
		if(!roleIndOpt.isPresent()) {
			RoleSet roleset = rolesetService.getRoleSetFromUserId(userId, baseDate);
			String roleID = roleset.getRoleIDByRoleType(RoleType.valueOf(roleType));
			return roleID;
		}
		
		return roleIndOpt.get().getRoleId();
	}

}
