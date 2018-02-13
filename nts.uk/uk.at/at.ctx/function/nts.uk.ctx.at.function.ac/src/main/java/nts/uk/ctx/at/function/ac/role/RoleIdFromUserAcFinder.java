package nts.uk.ctx.at.function.ac.role;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.role.RoleIdFromUserAdapter;
import nts.uk.ctx.sys.auth.pub.grant.RoleIndividualGrantExportRepo;

@Stateless
public class RoleIdFromUserAcFinder  implements RoleIdFromUserAdapter{
	@Inject
	private RoleIndividualGrantExportRepo roleIndRepo;

	@Override
	public List<String> getRoleIdFromUserId(String userId) {
		return roleIndRepo.getByUser(userId).stream().map(x ->x.roleId).collect(Collectors.toList());		
	}
}
