package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

/**
 * リポジトリ：雇用のフレックス時間勤務の月の集計設定
 * @author shuichu_ishida
 */
public interface AggrSettingMonthlyOfFlxForEmpRepository {
	
	/**
	 * 追加
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param aggrMonthlySettingOfFlx フレックス時間勤務の月の集計設定
	 */
	void insert(String companyId, String employmentCd, AggrSettingMonthlyOfFlx aggrMonthlySettingOfFlx);
	
	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param aggrMonthlySettingOfFlx フレックス時間勤務の月の集計設定
	 */
	void update(String companyId, String employmentCd, AggrSettingMonthlyOfFlx aggrMonthlySettingOfFlx);
}
