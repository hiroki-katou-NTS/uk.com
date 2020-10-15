package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp;

import java.util.Optional;

/**
 * The Interface EmploymentRegularSetMonthlyActualWorkRepository.
 */
public interface EmpRegulaMonthActCalSetRepo {

	/**
	 * Find by cid and empl code.
	 *
	 * @param cid the cid
	 * @param empCode the empl code
	 * @return the optional
	 */
	Optional<EmpRegulaMonthActCalSet> find(String cid, String empCode);

	/**
	 * Adds the.
	 *
	 * @param EmplRegSetMonthlyActualWork the empl reg set monthly actual work
	 */
	void add(EmpRegulaMonthActCalSet EmplRegSetMonthlyActualWork);

	/**
	 * Update.
	 *
	 * @param EmplRegSetMonthlyActualWork the empl reg set monthly actual work
	 */
	void update(EmpRegulaMonthActCalSet EmplRegSetMonthlyActualWork);

	/**
	 * Delete.
	 *
	 * @param EmplRegSetMonthlyActualWork the empl reg set monthly actual work
	 */
	void remove(String cid, String empCode);

}
