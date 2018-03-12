package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

/**
 * リポジトリ：会社のフレックス時間勤務の月の集計設定
 * @author shuichu_ishida
 */
public interface AggrSettingMonthlyOfFlxForCmpRepository {
	
	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param aggrSettingMonthlyOfFlx フレックス時間勤務の月の集計設定
	 */
	void update(String companyId, AggrSettingMonthlyOfFlx aggrSettingMonthlyOfFlx);
}
