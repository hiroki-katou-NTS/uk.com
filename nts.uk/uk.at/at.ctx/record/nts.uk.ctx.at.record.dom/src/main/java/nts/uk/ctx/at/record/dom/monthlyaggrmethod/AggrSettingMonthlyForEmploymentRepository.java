package nts.uk.ctx.at.record.dom.monthlyaggrmethod;

import java.util.Optional;

/**
 * リポジトリ：雇用月別実績集計設定
 * @author shuichu_ishida
 */
public interface AggrSettingMonthlyForEmploymentRepository {

	/**
	 * 検索
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @return 該当する雇用月別実績集計設定
	 */
	Optional<AggrSettingMonthlyForEmployment> find(String companyId, String employmentCd);
	
	/**
	 * 登録および更新
	 * @param aggrSettingMonthlyForEmployment 雇用月別実績集計設定
	 */
	void persistAndUpdate(AggrSettingMonthlyForEmployment aggrSettingMonthlyForEmployment);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 */
	void remove(String companyId, String employmentCd);
}
