package nts.uk.ctx.sys.auth.app.find.roleset;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetLinkWebMenuAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RoleSetFinder {

	@Inject
	private RoleSetRepository roleSetRepository;

	@Inject
	private RoleSetLinkWebMenuAdapter roleSetLinkWebMenuAdapter;
	
	/**
	 * Get a RoleSet
	 * @param roleSetCd
	 * @return
	 */
	public RoleSetDto find(String roleSetCd) {

		// get domain role set
		Optional<RoleSet> roleSetOpt = roleSetRepository.findByRoleSetCdAndCompanyId(roleSetCd, AppContexts.user().companyId());		
		if (roleSetOpt.isPresent()) {
			RoleSet roleSet = roleSetOpt.get();
			return RoleSetDto.build(roleSet, buildWebMenuDto(roleSet.getRoleSetCd().v()));			
		}

		return null;
	}

	/**
	 * Get all Role set by company id
	 * @return
	 */
	public List<RoleSetDto> findAll() {
		return this.roleSetRepository.findByCompanyId(AppContexts.user().companyId()).stream()
				.map(item -> RoleSetDto.build(item, buildWebMenuDto(item.getRoleSetCd().v())))
				.collect(Collectors.toList());
	}

	/**
	 * Build list of WebMenuDTO from RoleSetCd
	 * @param roleSetCd
	 * @return list of web menu dto.
	 */
	private List<WebMenuImportDto> buildWebMenuDto(String roleSetCd) {

		List<String> lstWebMenuCds = roleSetLinkWebMenuAdapter.findAllWebMenuByRoleSetCd(roleSetCd)
				.stream().map(item->item.getRoleSetCd()).collect(Collectors.toList());
		
		if (CollectionUtil.isEmpty(lstWebMenuCds)) {
			return null;
		}
		//build to DTO and return result
		return lstWebMenuCds.stream()
				.map(webMenuCd -> new WebMenuImportDto(webMenuCd, ""))
				.collect(Collectors.toList());
	}
}
