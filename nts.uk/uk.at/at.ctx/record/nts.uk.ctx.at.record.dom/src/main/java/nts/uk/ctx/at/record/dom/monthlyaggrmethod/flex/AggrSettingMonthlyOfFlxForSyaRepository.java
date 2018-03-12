package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

/**
 * リポジトリ：社員のフレックス時間勤務の月の集計設定
 * @author shuichu_ishida
 */
public interface AggrSettingMonthlyOfFlxForSyaRepository {
	
	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrMonthlySettingOfFlx フレックス時間勤務の月の集計設定
	 */
	void update(String companyId, String employeeId, AggrSettingMonthlyOfFlx aggrMonthlySettingOfFlx);
}
