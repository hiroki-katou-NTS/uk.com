package nts.uk.ctx.sys.auth.dom.roleset;

import java.util.List;

import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;

public interface RoleSetService {

	/**
	 * Get all Role Set - ロールセットをすべて取得する
	 * @return
	 */
	List<RoleSet> getAllRoleSet();
	
	/**
	 * Register Role Set - ロールセット新規登録
	 * @param roleSet
	 */
	void registerRoleSet(RoleSet roleSet);
	
	/**
	 * Update Role Set - ロールセット更新登録
	 * @param roleSet
	 */
	void updateRoleSet(RoleSet roleSet);
	
	/**
	 * Delete Role Set - ロールセット削除
	 * @param roleSetCd
	 */
	void deleteRoleSet(String roleSetCd);
}
