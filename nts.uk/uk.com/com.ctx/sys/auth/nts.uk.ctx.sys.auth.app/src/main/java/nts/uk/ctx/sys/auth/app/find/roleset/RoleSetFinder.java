package nts.uk.ctx.sys.auth.app.find.roleset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.sys.auth.dom.role.PersonRole;
import nts.uk.ctx.sys.auth.dom.role.PersonRoleRepository;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RoleSetFinder {

	@Inject
	private RoleSetRepository roleSetRepository;

	/**
	 * Get a RoleSet
	 * @param roleSetCd
	 * @return
	 */
	public RoleSetDto find(String roleSetCd) {
		//Get company Id
		String companyId = AppContexts.user().companyId();
		if (!StringUtils.isNoneEmpty(companyId)) {
			// get domain role set
			Optional<RoleSet> roleSetOpt = roleSetRepository.findByRoleSetCdAndCompanyId(roleSetCd, companyId);		
			if (roleSetOpt.isPresent()) {
				return RoleSetDto.build(roleSetOpt.get());			
			}
		}
		return new RoleSetDto();
	}

	/**
	 * Get all Role set by company id
	 * @return
	 */
	public List<RoleSetDto> findAll() {
		//Get company Id
		String companyId = AppContexts.user().companyId();
		if (!StringUtils.isNoneEmpty(companyId)) {
			return this.roleSetRepository.findByCompanyId(companyId).stream().map(item -> RoleSetDto.build(item))
					.collect(Collectors.toList());
		}
		return new ArrayList<RoleSetDto>();
	}


}
