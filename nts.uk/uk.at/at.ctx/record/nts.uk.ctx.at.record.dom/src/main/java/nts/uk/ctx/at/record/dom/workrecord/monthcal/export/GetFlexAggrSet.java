package nts.uk.ctx.at.record.dom.workrecord.monthcal.export;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;

/**
 * 集計設定の取得（フレックス）
 * @author shuichu_ishida
 */
public interface GetFlexAggrSet {

	/**
	 * 集計設定の取得（フレックス）
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @return フレックス時間勤務の月の集計設定
	 */
	Optional<FlexMonthWorkTimeAggrSet> get(
			String companyId, String employmentCd, String employeeId, GeneralDate criteriaDate);

	/**
	 * 集計設定の取得（フレックス）
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @return フレックス時間勤務の月の集計設定
	 */
	Optional<FlexMonthWorkTimeAggrSet> get(
			String companyId, String employmentCd, String employeeId, GeneralDate criteriaDate,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets);
}
