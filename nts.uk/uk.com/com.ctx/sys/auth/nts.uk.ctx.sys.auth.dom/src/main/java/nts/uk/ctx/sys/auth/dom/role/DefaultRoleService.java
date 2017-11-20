package nts.uk.ctx.sys.auth.dom.role;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRoleRepository;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSetRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class DefaultRoleService implements RoleService{

	@Inject 
	private RoleRepository roleRepo;
	@Inject
	private RoleIndividualGrantRepository roleGrantRepo;
	@Inject
	private DefaultRoleSetRepository defaultRoleSetRepo;
	@Inject
	private RoleSetRepository roleSetRepo;
	@Override
	public List<Role> getAllByType(RoleType roleType) {
		String companyId = AppContexts.programId();		
		return roleRepo.findByType(companyId, roleType.value);
	}

	@Override
	public void insertRole(Role role) {
		List<Role> roles = roleRepo.findByType( role.getRoleType().value);
		if(roles !=null && !roles.isEmpty()){
			roles.forEach(r ->{
				if(r.getRoleCode().equals(role.getRoleCode().toString())){
					throw new BusinessException("Msg_3");
				}
			});
		}		
		roleRepo.insert(role);
		
	}

	@Override
	public void updateRole(Role role) {
		role.canUpdate();
		roleRepo.update(role);		
	}

	@Override
	public void removeRole(String roleId, RoleAtr roleAtr) {
		String companyId = AppContexts.user().companyId();
		if (roleAtr == RoleAtr.INCHARGE) {
			List<RoleIndividualGrant> roleIndi = roleGrantRepo.findByRoleId(roleId);
			if (!roleIndi.isEmpty())
				throw new BusinessException("Msg_584");
			else
				roleRepo.remove(roleId);

		} else {
			Optional<DefaultRoleSet> defaultOpt = defaultRoleSetRepo.findByCompanyId(companyId);
			if (!defaultOpt.isPresent()) {
				DefaultRoleSet defaultRoleSet = defaultOpt.get();
				Optional<RoleSet> roleSetOpt = roleSetRepo
						.findByRoleSetCdAndCompanyId(defaultRoleSet.getRoleSetCd().toString(), companyId);
				if (roleSetOpt.isPresent() && roleSetOpt.get().getPersonInfRole().get().equals(roleId))
					throw new BusinessException("Msg_586");
			} else
				roleRepo.remove(roleId);				
		}
		
	}

}
