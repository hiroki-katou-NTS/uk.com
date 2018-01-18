package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

/**
 * リポジトリ：職場の変形労働の精算期間
 * @author shuichu_ishida
 */
public interface SettlementPeriodOfIrgForWkpRepository {

	/**
	 * 追加
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param settlementPeriod 精算期間
	 */
	void insert(String companyId, String workplaceId, SettlementPeriod settlementPeriod);

	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param settlementPeriod 精算期間
	 */
	void update(String companyId, String workplaceId, SettlementPeriod settlementPeriod);
	
	/**
	 * 削除　（親キー）
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 */
	void removeByParentPK(String companyId, String workplaceId);
}
