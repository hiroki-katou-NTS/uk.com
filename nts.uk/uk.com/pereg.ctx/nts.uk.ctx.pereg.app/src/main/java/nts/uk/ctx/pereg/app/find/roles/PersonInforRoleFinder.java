package nts.uk.ctx.pereg.app.find.roles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.roles.PersonInforRoleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PersonInforRoleFinder
 * 
 * @author lanlt
 *
 */
@Stateless
public class PersonInforRoleFinder {
	@Inject
	private PersonInforRoleRepository personRoleRepository;

	public List<PersonInforRoleDto> getAllPersonRole() {
		return this.personRoleRepository.getAllPersonRole().stream().map(item -> PersonInforRoleDto.fromDomain(item))
				.collect(Collectors.toList());
	}
	
	public Optional<PersonInforRoleDto> getDetailPersonRole(String roleId){
		String companyId = AppContexts.user().companyCode();
		
		return this.personRoleRepository.getDetailPersonRole(roleId,companyId).map(c-> PersonInforRoleDto.fromDomain(c));
	}
}
