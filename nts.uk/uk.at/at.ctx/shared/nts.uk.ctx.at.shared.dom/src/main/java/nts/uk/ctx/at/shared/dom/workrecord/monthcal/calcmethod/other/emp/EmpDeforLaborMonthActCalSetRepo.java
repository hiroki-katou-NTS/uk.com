package nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.emp;

import java.util.Optional;

/**
 * The Interface EmploymentLaborDeforSetTemporaryRepository.
 */
public interface EmpDeforLaborMonthActCalSetRepo {

	/**
	 * Find by id.
	 *
	 * @param cid the cid
	 * @param empCode the empl code
	 * @return the optional
	 */
	Optional<EmpDeforLaborMonthActCalSet> find(String cid, String empCode);

	/**
	 * Adds the.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void add(EmpDeforLaborMonthActCalSet emplLaborDeforSetTemporary);

	/**
	 * Update.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void update(EmpDeforLaborMonthActCalSet emplLaborDeforSetTemporary);

	/**
	 * Delete.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void remove(String cid, String empCode);
}
