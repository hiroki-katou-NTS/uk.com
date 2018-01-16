package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

/**
 * リポジトリ：会社の変形労働時間勤務の時間外超過設定
 * @author shuichu_ishida
 */
public interface ExcessOutsideTimeSetOfIrgForCmpRepository {

	/**
	 * 追加
	 * @param companyId 会社ID
	 * @param excessOutsideTimeSet 時間外超過設定
	 */
	void insert(String companyId, ExcessOutsideTimeSet excessOutsideTimeSet);

	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param excessOutsideTimeSet 時間外超過設定
	 */
	void update(String companyId, ExcessOutsideTimeSet excessOutsideTimeSet);
}
