package nts.uk.ctx.at.record.dom.monthlyaggrmethod;

import java.util.Optional;

/**
 * リポジトリ：社員月別実績集計設定
 * @author shuichu_ishida
 */
public interface AggrSettingMonthlyForSyainRepository {

	/**
	 * 検索
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @return 該当する社員月別実績集計設定
	 */
	Optional<AggrSettingMonthlyForSyain> find(String companyId, String employeeId);
	
	/**
	 * 登録および更新
	 * @param aggrSettingMonthlyForSyain 社員月別実績集計設定
	 */
	void persistAndUpdate(AggrSettingMonthlyForSyain aggrSettingMonthlyForSyain);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 */
	void remove(String companyId, String employeeId);
}
