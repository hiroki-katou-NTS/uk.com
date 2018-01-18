package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

/**
 * リポジトリ：社員の変形労働の精算期間
 * @author shuichu_ishida
 */
public interface SettlementPeriodOfIrgForSyaRepository {

	/**
	 * 追加
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param settlementPeriod 精算期間
	 */
	void insert(String companyId, String employeeId, SettlementPeriod settlementPeriod);

	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param settlementPeriod 精算期間
	 */
	void update(String companyId, String employeeId, SettlementPeriod settlementPeriod);
	
	/**
	 * 削除　（親キー）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 */
	void removeByParentPK(String companyId, String employeeId);
}
