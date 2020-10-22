package nts.uk.ctx.sys.portal.dom.toppagesetting;

import java.util.List;
import java.util.Optional;

/**
 * The Interface TopPagePersonSettingRepository.
 * Repository 個人別トップページ設定
 */
public interface TopPagePersonSettingRepository {

	/**
	 * Insert.
	 *
	 * @param domain the domain
	 */
	void insert(TopPagePersonSetting domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(TopPagePersonSetting domain);
	
	/**
	 * Delete.
	 *
	 * @param domain the domain
	 */
	void delete(TopPagePersonSetting domain);
	
	/**
	 * Gets the by company id and employee ids.
	 * 社員IDListから個人別トップページ設定を取得する
	 * @param companyId the company id
	 * @param employeeIds the employee ids
	 * @return the by company id and employee ids
	 */
	List<TopPagePersonSetting> getByCompanyIdAndEmployeeIds(String companyId, List<String> employeeIds);
	
	/**
	 * Gets the by company id and employee id.
	 * 社員IDから個人別トップページ設定を取得する
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @return the by company id and employee id
	 */
	Optional<TopPagePersonSetting> getByCompanyIdAndEmployeeId(String companyId, String employeeId);
	
}
