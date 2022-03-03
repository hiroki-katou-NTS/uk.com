package nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance;

import java.util.List;

/**
 * Smile連携受入設定Repository
 *
 */
public interface SmileCooperationAcceptanceSettingRepository {

	/**
	 * insert(Smile連携受入設定）
	 * 
	 * @param domain
	 */
	void insert(SmileCooperationAcceptanceSetting domain);

	/**
	 * update(Smile連携受入設定）
	 * 
	 * @param domain
	 */
	void update(SmileCooperationAcceptanceSetting domain);

	/**
	 * delete ログインしている契約コード、ログインしている会社ID
	 * 
	 * @param contractCode
	 * @param companyId
	 */
	void delete(String contractCode, String companyId);

	/**
	 * updateAll(List＜Smile連携受入設定＞)
	 */
	void updateAll(List<SmileCooperationAcceptanceSetting> smileCooperationAcceptanceSettings);

	/**
	 * insertAll(List＜Smile連携受入設定＞)
	 */
	void insertAll(List<SmileCooperationAcceptanceSetting> smileCooperationAcceptanceSettings);

	/**
	 * get 会社IDを指定してSmile連携受入設定を取得する
	 * 
	 * @param companyId 契約コード
	 * @param code      会社ID
	 * @return List SM連携受入設定
	 */
	List<SmileCooperationAcceptanceSetting> get(String contractCode, String companyId);
}
