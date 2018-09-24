package nts.uk.ctx.at.record.dom.monthly.remarks;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RemarksMonthlyRecordRepository {
	
	/**
	 * Find by YearMonth, ClosureId & ClosureDate
	 * @param employeeId
	 * @param yearMonth
	 * @param closureId
	 * @param closureDate
	 * @return
	 */
	Optional<RemarksMonthlyRecord> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * Find by YearMonth
	 * @param employeeId
	 * @param yearMonth
	 * @return
	 */
	List<RemarksMonthlyRecord> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth);
	
	/**
	 * 
	 * @param employeeId
	 * @param yearMonth
	 * @param closureId
	 * @return
	 */
	List<RemarksMonthlyRecord> findByYMAndClosureIdOrderByStartYmd(
			String employeeId, YearMonth yearMonth, ClosureId closureId);
	
	/**
	 * 
	 * @param employeeIds
	 * @param yearMonth
	 * @param closureId
	 * @param closureDate
	 * @return
	 */
	List<RemarksMonthlyRecord> findByEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);

	/**
	 * 
	 * @param employeeIds
	 * @param yearMonths
	 * @return
	 */
	List<RemarksMonthlyRecord> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths);
	
	/**
	 * 
	 * @param employeeId
	 * @param criteriaDate
	 * @return
	 */
	List<RemarksMonthlyRecord> findByDate(String employeeId, GeneralDate criteriaDate);

	/**
	 * 
	 * @param employeeId
	 * @param period
	 * @return
	 */
	List<RemarksMonthlyRecord> findByPeriodIntoEndYmd(String employeeId, DatePeriod period);

	/**
	 * 
	 * @param attendanceTimeOfMonthly
	 */
	void persistAndUpdate(RemarksMonthlyRecord remarksMonthlyRecord);

	/**
	 * 
	 * @param employeeId
	 * @param yearMonth
	 * @param closureId
	 * @param closureDate
	 */
	void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);

	/**
	 * 
	 * @param employeeId
	 * @param yearMonth
	 */
	void removeByYearMonth(String employeeId, YearMonth yearMonth);
}