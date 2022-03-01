package nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput;

import java.util.List;

/**
 * 連動支払変換Repository
 *
 */
public interface LinkedPaymentConversionRepository {
	
	/**
	 * insert(連動支払変換）
	 * 
	 * @param domain
	 */
	void insert(LinkedPaymentConversion domain);

	/**
	 * update(連動支払変換）
	 * 
	 * @param domain
	 */
	void update(LinkedPaymentConversion domain);

	/**
	 * delete(ログインしている契約コード、ログインしている会社ID)
	 * 
	 * @param contractCode
	 * @param companyId
	 */
	void delete(String contractCode, String companyId);
	
	/**
	 * delete 支払コードを指定して連動支払変換を消す(ログインしている契約コード、ログインしている会社ID、支払コード)
	 * @param contractCode
	 * @param companyId
	 * @param paymentCode
	 */
	void delete(String contractCode, String companyId, PaymentCategory paymentCode);

	/**
	 * get 支払コードを指定して連動支払変換を取得する
	 * 
	 * @param companyId 契約コード
	 * @param code      会社ID
	 * @param paymentCode 支払区分
	 * @return
	 */
	List<EmploymentAndLinkedMonthSetting> getByPaymentCode(String contractCode, String companyId, PaymentCategory paymentCode);
	
	/**
	 * get 会社を指定して選択雇用一覧を取得する
	 * 
	 * @param companyId 契約コード
	 * @param code      会社ID
	 * @return
	 */
	List<EmploymentAndLinkedMonthSetting> get(String contractCode, String companyId);
}
