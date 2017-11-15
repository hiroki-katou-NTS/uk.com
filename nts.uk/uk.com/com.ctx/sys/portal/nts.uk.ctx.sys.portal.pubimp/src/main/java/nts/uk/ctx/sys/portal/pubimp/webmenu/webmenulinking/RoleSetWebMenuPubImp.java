/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.pubimp.webmenu.webmenulinking;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetAndWebMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetAndWebMenuRepository;
import nts.uk.ctx.sys.portal.pub.webmenu.webmenulinking.RoleSetAndWebMenuPub;
import nts.uk.ctx.sys.portal.pub.webmenu.webmenulinking.RoleSetWebMenuPubDto;

/**
 * The Class WebmenuPubImp.
 */
@Stateless
public class RoleSetWebMenuPubImp implements RoleSetAndWebMenuPub {

	/** The role set and web menu link repository. */
	@Inject
	private RoleSetAndWebMenuRepository roleSetAndWebMenuRepository;

	@Override
	public List<RoleSetWebMenuPubDto> findAllWebMenuByRoleSetCd(String companyId, String roleSetCd) {
		return roleSetAndWebMenuRepository.findByRoleSetCd(companyId, roleSetCd).stream()
				.map(item -> new RoleSetWebMenuPubDto(
						item.getRoleSetCd().v()
						, item.getWebMenuCd().v()
						, item.getCompanyId())
						)
				.collect(Collectors.toList());
	}

	@Override
	public void addListOfRoleSetAndWebMenu(List<RoleSetWebMenuPubDto> roleSetWebMenuPubDto) {
		if (!CollectionUtil.isEmpty(roleSetWebMenuPubDto)) {
			roleSetWebMenuPubDto.forEach(item -> {
				RoleSetAndWebMenu domain = new RoleSetAndWebMenu(
						item.getRoleSetCd()
						, item.getWebMenuCd()
						, item.getCompanyId());
				roleSetAndWebMenuRepository.insert(domain);
			});
		}
	}

	@Override
	public void updateListOfRoleSetAndWebMenu(List<RoleSetWebMenuPubDto> roleSetWebMenuPubDto) {
		if (!CollectionUtil.isEmpty(roleSetWebMenuPubDto)) {
			roleSetWebMenuPubDto.forEach(item -> {
				RoleSetAndWebMenu domain = new RoleSetAndWebMenu(
						item.getRoleSetCd()
						, item.getWebMenuCd()
						, item.getCompanyId());
				roleSetAndWebMenuRepository.update(domain);
			});
		}
	}

	@Override
	public void deleteRoleSetAndWebMenuByCompanyIdAndRoleSetCd(List<RoleSetWebMenuPubDto> roleSetWebMenuPubDto) {
		if (!CollectionUtil.isEmpty(roleSetWebMenuPubDto)) {
			roleSetWebMenuPubDto.forEach(item -> {
				RoleSetAndWebMenu domain = new RoleSetAndWebMenu(
						item.getRoleSetCd()
						, item.getWebMenuCd()
						, item.getCompanyId());
				roleSetAndWebMenuRepository.delete(domain);
			});
		}
	}
	
	
}
