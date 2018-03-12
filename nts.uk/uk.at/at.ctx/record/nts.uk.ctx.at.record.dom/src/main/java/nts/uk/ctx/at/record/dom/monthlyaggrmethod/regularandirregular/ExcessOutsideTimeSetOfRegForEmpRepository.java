package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

/**
 * リポジトリ：雇用の通常勤務の時間外超過設定
 * @author shuichu_ishida
 */
public interface ExcessOutsideTimeSetOfRegForEmpRepository {

	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param excessOutsideTimeSet 時間外超過設定
	 */
	void update(String companyId, String employmentCd, ExcessOutsideTimeSet excessOutsideTimeSet);
}
