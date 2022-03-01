package nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput;

/**
 * Smile連携出力設定Repository										
 *
 */
public interface SmileLinkageOutputSettingRepository {
	/**
	 * insert(Smile連携出力設定）
	 * 
	 * @param domain
	 */
	void insert(SmileLinkageOutputSetting domain);

	/**
	 * update(Smile連携出力設定）
	 * 
	 * @param domain
	 */
	void update(SmileLinkageOutputSetting domain);

	/**
	 * delete ログインしている契約コード、ログインしている会社ID
	 * 
	 * @param contractCode
	 * @param companyId
	 */
	void delete(String contractCode, String companyId);

	/**
	 * get 会社IDを指定してSmile連携出力設定を取得する
	 * 
	 * @param companyId 契約コード
	 * @param code      会社ID
	 * @return
	 */
	SmileLinkageOutputSetting get(String contractCode, String companyId);
}
