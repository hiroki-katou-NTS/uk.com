package nts.uk.ctx.at.record.dom.monthlyaggrmethod;

import java.util.Optional;

/**
 * 月別実績集計設定を取得する
 * @author shuichu_ishida
 */
public interface GetAggrSettingMonthly {

	/**
	 * 取得
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @return 月別実績集計設定
	 */
	Optional<AggrSettingMonthly> get(String companyId, String workplaceId, String employmentCd, String employeeId);
}
