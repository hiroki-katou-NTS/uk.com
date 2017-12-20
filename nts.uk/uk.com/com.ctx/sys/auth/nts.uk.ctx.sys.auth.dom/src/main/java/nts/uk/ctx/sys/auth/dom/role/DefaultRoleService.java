package nts.uk.ctx.sys.auth.dom.role;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
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
	public void removeRole(String roleId) {
		String companyId = AppContexts.user().companyId();
		Role role = roleRepo.findByRoleId(roleId).get();		
		if (role.getAssignAtr() == RoleAtr.INCHARGE) {			
			List<RoleIndividualGrant> roleIndi = roleGrantRepo.findByRoleId(roleId);
			if (!roleIndi.isEmpty()) {
				GeneralDate now =  GeneralDate.today();
				roleIndi.forEach(x ->{
					if(x.getValidPeriod().contains(now)) {
						 throw new BusinessException("Msg_584");
					}
				});							
			}					
			roleRepo.remove(roleId);				
		} else {
			Optional<DefaultRoleSet> defaultOpt = defaultRoleSetRepo.findByCompanyId(companyId);
			if (defaultOpt.isPresent()) {
				DefaultRoleSet defaultRoleSet = defaultOpt.get();
				Optional<RoleSet> roleSetOpt = roleSetRepo
						.findByRoleSetCdAndCompanyId(defaultRoleSet.getRoleSetCd().toString(), companyId);
				if (roleSetOpt.isPresent()){
					RoleSet rs = roleSetOpt.get();
					if ((rs.getPersonInfRoleId() != null && rs.getPersonInfRoleId().equals(roleId))
							|| (rs.getSalaryRoleId() != null && rs.getSalaryRoleId().equals(roleId))
							|| (rs.getOfficeHelperRoleId() != null && rs.getOfficeHelperRoleId().equals(roleId))
							|| (rs.getHRRoleId() != null && rs.getHRRoleId().equals(roleId))
							|| (rs.getEmploymentRoleId() != null && rs.getEmploymentRoleId().equals(roleId))
							|| (rs.getMyNumberRoleId() != null && rs.getMyNumberRoleId().equals(roleId)))
						throw new BusinessException("Msg_586");
				} 
			} 		
			roleRepo.remove(roleId);											
		}
		
	}

}
