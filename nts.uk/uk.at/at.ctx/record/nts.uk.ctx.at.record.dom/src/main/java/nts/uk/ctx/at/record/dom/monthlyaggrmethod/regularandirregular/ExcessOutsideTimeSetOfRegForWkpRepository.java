package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

/**
 * リポジトリ：職場の通常勤務の時間外超過設定
 * @author shuichu_ishida
 */
public interface ExcessOutsideTimeSetOfRegForWkpRepository {

	/**
	 * 追加
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param excessOutsideTimeSet 時間外超過設定
	 */
	void insert(String companyId, String workplaceId, ExcessOutsideTimeSet excessOutsideTimeSet);

	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param excessOutsideTimeSet 時間外超過設定
	 */
	void update(String companyId, String workplaceId, ExcessOutsideTimeSet excessOutsideTimeSet);
}
