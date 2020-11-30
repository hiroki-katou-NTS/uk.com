package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp;

import java.util.List;
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
	 * 
	 * @param cid : 会社ID
	 * @return
	 */
	List<EmpDeforLaborMonthActCalSet> findEmpDeforLabor(String cid);

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
