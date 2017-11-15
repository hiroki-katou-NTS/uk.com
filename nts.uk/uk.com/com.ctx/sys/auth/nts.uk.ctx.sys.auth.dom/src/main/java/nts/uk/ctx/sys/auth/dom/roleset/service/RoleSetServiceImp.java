package nts.uk.ctx.sys.auth.dom.roleset.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RoleSetServiceImp implements RoleSetService{

	@Inject RoleSetRepository roleSetRepository;
	/**
	 * Get all Role Set - ロールセットをすべて取得する
	 * @return
	 */
	@Override
	public
	List<RoleSet> getAllRoleSet() {
		String companyId = AppContexts.user().companyId();
		if (StringUtils.isNoneEmpty(companyId)) {
			return null;	
		}
			
		return roleSetRepository.findByCompanyId(companyId);
	}
	
	/**
	 * Register Role Set - ロールセット新規登録
	 * @param roleSet
	 */
	@Override
	public
	void registerRoleSet(RoleSet roleSet) {
		
	}
	
	/**
	 * Update Role Set - ロールセット更新登録
	 * @param roleSet
	 */
	@Override
	public
	void updateRoleSet(RoleSet roleSet) {
		
	}
	
	/**
	 * Delete Role Set - ロールセット削除
	 * @param roleSetCd
	 */
	@Override
	public
	void deleteRoleSet(String roleSetCd) {
		
	}
}
