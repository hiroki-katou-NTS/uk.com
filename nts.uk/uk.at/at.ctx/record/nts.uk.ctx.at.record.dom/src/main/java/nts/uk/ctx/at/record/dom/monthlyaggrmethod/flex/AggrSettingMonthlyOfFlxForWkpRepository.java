package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

/**
 * リポジトリ：職場のフレックス時間勤務の月の集計設定
 * @author shuichu_ishida
 */
public interface AggrSettingMonthlyOfFlxForWkpRepository {
	
	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param aggrMonthlySettingOfFlx フレックス時間勤務の月の集計設定
	 */
	void update(String companyId, String workplaceId, AggrSettingMonthlyOfFlx aggrMonthlySettingOfFlx);
}
