package nts.uk.ctx.at.record.dom.monthlyaggrmethod;

import java.util.Optional;

/**
 * リポジトリ：会社月別実績集計設定
 * @author shuichu_ishida
 */
public interface AggrSettingMonthlyForCompanyRepository {

	/**
	 * 検索
	 * @param companyId 会社ID
	 * @return 該当する会社月別実績集計設定
	 */
	Optional<AggrSettingMonthlyForCompany> find(String companyId);
	
	/**
	 * 追加
	 * @param aggrSettingMonthlyForCompany 会社月別実績集計設定
	 */
	void insert(AggrSettingMonthlyForCompany aggrSettingMonthlyForCompany);
	
	/**
	 * 更新
	 * @param aggrSettingMonthlyForCompany 会社月別実績集計設定
	 */
	void update(AggrSettingMonthlyForCompany aggrSettingMonthlyForCompany);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 */
	void remove(String companyId);
}
