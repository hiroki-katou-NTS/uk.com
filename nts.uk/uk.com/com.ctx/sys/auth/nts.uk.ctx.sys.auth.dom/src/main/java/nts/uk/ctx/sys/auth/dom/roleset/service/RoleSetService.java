package nts.uk.ctx.sys.auth.dom.roleset.service;

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
	
	/**
	 * アルゴリズム「新規登録」を実行する - Execute the algorithm "new registration"
	 * @param roleSet
	 * @param menuCds
	 */
	void executeRegister(RoleSet roleSet, List<String> webMenuCds);
	
	
	/**
	 * アルゴリズム「更新登録」を実行する - Execute algorithm "update registration"
	 * @param roleSet
	 * @param webMenuCds
	 */
	void executeUpdate(RoleSet roleSet, List<String> webMenuCds);
	
	/**
	 * アルゴリズム「削除」を実行する - Execute algorithm "delete"
	 * @param roleSetCd
	 */
	void executeDelete(String roleSetCd);
}
