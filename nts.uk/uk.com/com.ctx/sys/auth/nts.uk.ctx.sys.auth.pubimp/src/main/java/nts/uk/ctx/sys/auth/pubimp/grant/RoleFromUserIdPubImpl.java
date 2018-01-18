package nts.uk.ctx.sys.auth.pubimp.grant;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.pub.grant.RoleFromUserIdPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RoleFromUserIdPubImpl implements RoleFromUserIdPub{

	@Inject
	private RoleIndividualGrantRepository roleIndRepo;
	
	@Override
	public String getRoleFromUserId(String userId, int roleType, GeneralDate baseDate) {
		
		String companyId = AppContexts.user().companyId();		
		Optional<RoleIndividualGrant> roleIndOpt = roleIndRepo.findByUserCompanyRoleTypeDate(userId, companyId, roleType, baseDate);
		if(!roleIndOpt.isPresent()) {
			
			
			return null; 			
		}
		
		return roleIndOpt.get().getRoleId();
	}

}
