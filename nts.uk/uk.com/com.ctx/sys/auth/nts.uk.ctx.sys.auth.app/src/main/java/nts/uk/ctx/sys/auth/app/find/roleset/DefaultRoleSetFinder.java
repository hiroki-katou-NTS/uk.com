package nts.uk.ctx.sys.auth.app.find.roleset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DefaultRoleSetFinder {

	@Inject
	private DefaultRoleSetRepository defaultRoleSetRepository;

	/**
	 * Get a Default Role Set by company id and role set code
	 * @param roleSetCd
	 * @return
	 */
	public DefaultRoleSetDto find(String roleSetCd) {
		//Get company Id
		String companyId = AppContexts.user().companyId();
		if (!StringUtils.isNoneEmpty(companyId)) {
			// get domain role set
			Optional<DefaultRoleSet> defaultRoleSetOpt = defaultRoleSetRepository.find(companyId, roleSetCd);		
			if (defaultRoleSetOpt.isPresent()) {
				return DefaultRoleSetDto.build(defaultRoleSetOpt.get());			
			}
		}
		return new DefaultRoleSetDto();
	}

	/**
	 * Get all Default Role Set by company id
	 * @return
	 */
	public DefaultRoleSetDto findByCompanyId() {
		//Get company Id
		String companyId = AppContexts.user().companyId();
		if (!StringUtils.isNoneEmpty(companyId)) {
			// get domain role set
			Optional<DefaultRoleSet> defaultRoleSetOpt =  this.defaultRoleSetRepository.findByCompanyId(companyId);
			if (defaultRoleSetOpt.isPresent()) {
				return DefaultRoleSetDto.build(defaultRoleSetOpt.get());			
			}
		}
		return new DefaultRoleSetDto();
	}


}
