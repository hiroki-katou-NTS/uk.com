package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;


/**
 *　リポジトリ：子の看護休暇月別残数データ
  * @author yuri_tamakoshi
 */
public interface ChildcareNurseRemNumEachMonthRepository {

	/**
	 * 子の看護休暇月別残数データの検索
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureStatus 締め処理状態
	 * @param closureDate 締め日付
	 * @return 該当する子の看護休暇月別残数データ
	 */
	Optional<ChildcareNurseRemNumEachMonth> find(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);

	/**
	 * 検索　（年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @return 子の看護休暇月別残数データ　（開始年月日順）
	 */
	List<ChildcareNurseRemNumEachMonth> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth);

	/**
	 * 検索　（年月と締めID）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @return 子の看護休暇月別残数データ　（開始年月日日順）
	 */
	List<ChildcareNurseRemNumEachMonth> findByYMAndClosureIdOrderByStartYmd(
			String employeeId, YearMonth yearMonth, ClosureId closureId);

	/**
	 * 検索　（社員IDリスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @return 該当する子の看護休暇月別残数データ
	 */
	List<ChildcareNurseRemNumEachMonth> findbyEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);

	/**
	 * 検索　（社員IDリストと年月リスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonths 年月リスト
	 * @return 子の看護休暇月別残数データ　（開始年月日順）
	 */
	List<ChildcareNurseRemNumEachMonth> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths);

	/**
	 * 検索　（社員IDと締め期間、条件＝締め済み）
	 * @param employeeId 社員ID
	 * @param closurePeriod 締め期間
	 * @return 子の看護休暇月別残数データ　（締め開始日順）
	 */
	List<ChildcareNurseRemNumEachMonth> findByClosurePeriod(String employeeId, DatePeriod closurePeriod);

	/**
	 * 登録および更新
	 * @param domain 子の看護休暇月別残数データ
	 */
	void persistAndUpdate(ChildcareNurseRemNumEachMonth domain);

//	/**
//	 * 削除
//	 * @param employeeId 社員ID
//	 * @param yearMonth 年月
//	 * @param closureId 締めID
//	 * @param closureDate 締め日付
//	 */
//	void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);

	/**
	 * 削除　（年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 */
	void removeByYearMonth(String employeeId, YearMonth yearMonth);

	void remove(String employeeId, YearMonth yearMonth, NursingCategory nursingType);



	Optional<ChildCareNurseUsedNumber> find(String employeeId, int nursingType);


}
