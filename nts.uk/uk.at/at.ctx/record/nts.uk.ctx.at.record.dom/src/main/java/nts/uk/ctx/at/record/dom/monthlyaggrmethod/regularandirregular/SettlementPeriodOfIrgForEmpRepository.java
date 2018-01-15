package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

/**
 * リポジトリ：雇用の変形労働の精算期間
 * @author shuichu_ishida
 */
public interface SettlementPeriodOfIrgForEmpRepository {

	/**
	 * 追加
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param settlementPeriod 精算期間
	 */
	void insert(String companyId, String employmentCd, SettlementPeriod settlementPeriod);

	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param settlementPeriod 精算期間
	 */
	void update(String companyId, String employmentCd, SettlementPeriod settlementPeriod);
	
	/**
	 * 削除　（親キー）
	 * @param companyId 会社コード
	 * @param employmentCd 雇用ID
	 */
	void removeByParentPK(String companyId, String employmentCd);
}
