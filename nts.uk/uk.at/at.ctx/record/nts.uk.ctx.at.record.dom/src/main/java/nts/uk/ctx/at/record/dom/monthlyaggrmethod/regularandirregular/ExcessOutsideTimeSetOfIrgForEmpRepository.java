package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

/**
 * リポジトリ：雇用の変形労働時間勤務の時間外超過設定
 * @author shuichu_ishida
 */
public interface ExcessOutsideTimeSetOfIrgForEmpRepository {

	/**
	 * 追加
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param excessOutsideTimeSet 時間外超過設定
	 */
	void insert(String companyId, String employmentCd, ExcessOutsideTimeSet excessOutsideTimeSet);

	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param excessOutsideTimeSet 時間外超過設定
	 */
	void update(String companyId, String employmentCd, ExcessOutsideTimeSet excessOutsideTimeSet);
}
