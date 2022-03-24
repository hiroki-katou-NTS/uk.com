package nts.uk.ctx.at.shared.dom.workrule.specific;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;

/**
 * The Interface SpecificWorkRuleRepository.
 * @author HoangNDH
 */
public interface SpecificWorkRuleRepository {
	
	/**
	 * Find calc method by cid.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	public Optional<CalculateOfTotalConstraintTime> findCalcMethodByCid(String companyId);
	
	/**
	 * Find time off vacation order by cid.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	public Optional<CompanyHolidayPriorityOrder> findTimeOffVacationOrderByCid(String companyId);
	
	/**
	 * Find upper limit wk hour by cid.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	public Optional<UpperLimitTotalWorkingHour> findUpperLimitWkHourByCid(String companyId);
	
	/**
	 * Insert calc method.
	 *
	 * @param setting the setting
	 */
	public void insertCalcMethod(CalculateOfTotalConstraintTime setting);
	
	/**
	 * Insert time off vacation order.
	 *
	 * @param setting the setting
	 */
	public void insertTimeOffVacationOrder(CompanyHolidayPriorityOrder setting);
	
	/**
	 * Insert upper limit wk hour.
	 *
	 * @param setting the setting
	 */
	public void insertUpperLimitWkHour(UpperLimitTotalWorkingHour setting);
	
	/**
	 * Update calc method.
	 *
	 * @param setting the setting
	 */
	public void updateCalcMethod(CalculateOfTotalConstraintTime setting);
	
	/**
	 * Update time off vacation order.
	 *
	 * @param setting the setting
	 */
	public void updateTimeOffVacationOrder(CompanyHolidayPriorityOrder setting);
	
	/**
	 * Update upper limit wk hour.
	 *
	 * @param setting the setting
	 */
	public void updateUpperLimitWkHour(UpperLimitTotalWorkingHour setting);
}
