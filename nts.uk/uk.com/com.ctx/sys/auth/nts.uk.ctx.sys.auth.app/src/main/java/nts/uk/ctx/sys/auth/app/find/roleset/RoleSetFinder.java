package nts.uk.ctx.sys.auth.app.find.roleset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.WebMenuImport;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.WebMenuAdapter;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenuAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RoleSetFinder {

	@Inject
	private RoleSetRepository roleSetRepository;

	@Inject
	private WebMenuAdapter webMenuAdapter;
	
	@Inject
	private RoleSetAndWebMenuAdapter roleSetAndWebMenuAdapter;
	
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
				RoleSet roleSet = roleSetOpt.get();
				return RoleSetDto.build(roleSet, buildWebMenuDto(roleSet.getRoleSetCd().v()));			
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
			return this.roleSetRepository.findByCompanyId(companyId).stream()
					.map(item -> RoleSetDto.build(item, buildWebMenuDto(item.getRoleSetCd().v())))
					.collect(Collectors.toList());
		}
		return new ArrayList<RoleSetDto>();
	}

	/**
	 * Build list of WebMenuDTO from RoleSetCd
	 * @param roleSetCd
	 * @return list of web menu dto.
	 */
	private List<WebMenuDto> buildWebMenuDto(String roleSetCd) {

		List<String> lstWebMenuCds = roleSetAndWebMenuAdapter.findAllWebMenuByRoleSetCd(roleSetCd)
				.stream().map(item->item.getRoleSetCd()).collect(Collectors.toList());
		
		if (CollectionUtil.isEmpty(lstWebMenuCds)) {
			return null;
		}

		List<WebMenuImport> lstWebMenus = webMenuAdapter.findByCompanyId();
		if (CollectionUtil.isEmpty(lstWebMenus)) {
			return null;
		}

		List<WebMenuDto> retWebmenus = lstWebMenuCds.stream().map(item -> {
			Optional<WebMenuImport> webMenuOpt = lstWebMenus.stream().filter(wmn-> wmn.getWebMenuCd().equals(item)).findFirst();
			if (webMenuOpt.isPresent()) {
				WebMenuImport wmn = webMenuOpt.get();
				return new WebMenuDto(wmn.getWebMenuCd(), wmn.getWebMenuName());
			}
			return null;
			}).collect(Collectors.toList());
		if (CollectionUtil.isEmpty(retWebmenus)) {
			return null;
		}
		return retWebmenus.stream().filter(item -> item != null).collect(Collectors.toList());
	}


}
