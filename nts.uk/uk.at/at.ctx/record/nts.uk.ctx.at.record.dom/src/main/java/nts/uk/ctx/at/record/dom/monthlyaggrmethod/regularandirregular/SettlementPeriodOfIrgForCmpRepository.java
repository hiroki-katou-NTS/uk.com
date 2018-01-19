package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

/**
 * リポジトリ：会社の変形労働の精算期間
 * @author shuichu_ishida
 */
public interface SettlementPeriodOfIrgForCmpRepository {

	/**
	 * 追加
	 * @param companyId 会社ID
	 * @param settlementPeriod 精算期間
	 */
	void insert(String companyId, SettlementPeriod settlementPeriod);

	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param settlementPeriod 精算期間
	 */
	void update(String companyId, SettlementPeriod settlementPeriod);
	
	/**
	 * 削除　（親キー）
	 * @param companyId 会社ID
	 */
	void removeByParentPK(String companyId);
}
