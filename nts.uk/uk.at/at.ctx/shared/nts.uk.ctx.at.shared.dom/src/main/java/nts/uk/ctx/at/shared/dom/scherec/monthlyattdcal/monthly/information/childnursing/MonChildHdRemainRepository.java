package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.information.childnursing;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.ChildcareRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

public interface MonChildHdRemainRepository {

	/**
	 * Find by YearMonth, ClosureId & ClosureDate
	 * @param employeeId
	 * @param yearMonth
	 * @param closureId
	 * @param closureDate
	 * @return
	 */
	Optional<ChildcareRemNumEachMonth> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * Find by YearMonth
	 * @param employeeId
	 * @param yearMonth
	 * @return
	 */
	List<ChildcareRemNumEachMonth> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth);
	
	/**
	 * 
	 * @param employeeId
	 * @param yearMonth
	 * @param closureId
	 * @return
	 */
	List<ChildcareRemNumEachMonth> findByYMAndClosureIdOrderByStartYmd(
			String employeeId, YearMonth yearMonth, ClosureId closureId);
	
	/**
	 * 
	 * @param employeeIds
	 * @param yearMonth
	 * @param closureId
	 * @param closureDate
	 * @return
	 */
	List<ChildcareRemNumEachMonth> findByEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);

	/**
	 * 
	 * @param employeeIds
	 * @param yearMonths
	 * @return
	 */
	List<ChildcareRemNumEachMonth> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths);

	/**
	 * 
	 * @param attendanceTimeOfMonthly
	 */
	void persistAndUpdate(ChildcareRemNumEachMonth remarksMonthlyRecord);

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
