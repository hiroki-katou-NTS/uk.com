package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

/**
 * リポジトリ：会社の通常勤務の時間外超過設定
 * @author shuichu_ishida
 */
public interface ExcessOutsideTimeSetOfRegForCmpRepository {

	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param excessOutsideTimeSet 時間外超過設定
	 */
	void update(String companyId, ExcessOutsideTimeSet excessOutsideTimeSet);
}
