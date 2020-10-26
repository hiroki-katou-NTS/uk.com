package nts.uk.ctx.sys.portal.dom.toppagesetting;

import java.util.List;
import java.util.Optional;

/**
 * The Interface TopPageRoleSettingRepository.
 * Repository 権限別トップページ設定
 */
public interface TopPageRoleSettingRepository {

	/**
	 * Insert.
	 * [1]  insert(権限別トップページ設定)																									
	 * @param domain the domain
	 */
	void insert(TopPageRoleSetting domain);
	
	/**
	 * Update.
	 * [2]  update(権限別トップページ設定)																									
	 * @param domain the domain
	 */
	void update(TopPageRoleSetting domain);
	
	/**
	 * Delete.
	 * [3]  delete(権限別トップページ設定)																									
	 * @param domain the domain
	 */
	void delete(TopPageRoleSetting domain);
	
	/**
	 * Gets the by company id.
	 * 会社IDからトップページを取得する
	 * @param companyId the company id
	 * @return the by company id
	 */
	List<TopPageRoleSetting> getByCompanyId(String companyId);
	
	/**
	 * Gets the by company id and role set code.
	 * 会社ID、ロールセットコードから権限別トップページ設定を取得する
	 * @param companyId the company id
	 * @param roleSetCode the role set code
	 * @return the by company id and role set code
	 */
	Optional<TopPageRoleSetting> getByCompanyIdAndRoleSetCode(String companyId, String roleSetCode);
}
