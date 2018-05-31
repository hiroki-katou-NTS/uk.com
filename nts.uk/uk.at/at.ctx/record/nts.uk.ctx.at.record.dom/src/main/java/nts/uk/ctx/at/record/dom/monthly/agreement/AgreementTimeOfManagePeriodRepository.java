package nts.uk.ctx.at.record.dom.monthly.agreement;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * リポジトリ：管理期間の36協定時間
 * @author shuichu_ishida
 */
public interface AgreementTimeOfManagePeriodRepository {

	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @return 該当する管理期間の36協定時間
	 */
	Optional<AgreementTimeOfManagePeriod> find(String employeeId, YearMonth yearMonth);

	/**
	 * 検索　（年度）
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @return 年度に該当する管理期間の36協定時間　（年月順）
	 */
	List<AgreementTimeOfManagePeriod> findByYearOrderByYearMonth(String employeeId, Year year);

	/**
	 * 検索　（社員IDリスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonth 年月
	 * @return 該当する管理期間の36協定時間
	 */
	List<AgreementTimeOfManagePeriod> findByEmployees(List<String> employeeIds, YearMonth yearMonth);

	/**
	 * 検索　（社員IDリストと年月リスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonths 年月リスト
	 * @return 該当する管理期間の36協定時間
	 */
	List<AgreementTimeOfManagePeriod> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths);
	
	/**
	 * 登録および更新
	 * @param agreementTimeOfManagePeriod 管理期間の36協定時間
	 */
	void persistAndUpdate(AgreementTimeOfManagePeriod agreementTimeOfManagePeriod);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 */
	void remove(String employeeId, YearMonth yearMonth);
	
	/**
	 * 削除　（年度）
	 * @param employeeId 社員ID
	 * @param year 年度
	 */
	void removeByYear(String employeeId, Year year);
}
