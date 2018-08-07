package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import java.util.List;
import java.util.Optional;

/**
 * The Interface OutputItemMonthlyWorkScheduleRepository.
 */
public interface OutputItemMonthlyWorkScheduleRepository {

	/**
	 * Find by cid and code.
	 *
	 * @param companyId the company id
	 * @param code the code
	 * @return the optional
	 */
	public Optional<OutputItemMonthlyWorkSchedule> findByCidAndCode(String companyId, String code);
	
	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<OutputItemMonthlyWorkSchedule> findByCid(String companyId);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(OutputItemMonthlyWorkSchedule domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(OutputItemMonthlyWorkSchedule domain);
	
	/**
	 * Delete.
	 *
	 * @param domain the domain
	 */
	void delete(OutputItemMonthlyWorkSchedule domain);
	
	/**
	 * Delete by cid and code.
	 *
	 * @param companyId the company id
	 * @param code the code
	 */
	void deleteByCidAndCode(String companyId, String code);
}
