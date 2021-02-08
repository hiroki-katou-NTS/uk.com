package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmployeeRegularSetMonthlyActualRepository.
 */
public interface ShaRegulaMonthActCalSetRepo {

	/**
	 * Find emp reg set monthly actual by cid and emp id.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @return the optional
	 */
	Optional<ShaRegulaMonthActCalSet> find(String cid, String sId);
	
	/**
	 * Find by Cid
	 * @param cid
	 * @return
	 */
	List<ShaRegulaMonthActCalSet> findRegulaMonthActCalSetByCid(String cid);

	/**
	 * Adds the.
	 *
	 * @param empRegSetMonthlyActual the emp reg set monthly actual
	 */
	void add(ShaRegulaMonthActCalSet empRegSetMonthlyActual);

	/**
	 * Update.
	 *
	 * @param empRegSetMonthlyActual the emp reg set monthly actual
	 */
	void update(ShaRegulaMonthActCalSet empRegSetMonthlyActual);

	/**
	 * Delete.
	 *
	 * @param empRegSetMonthlyActual the emp reg set monthly actual
	 */
	void remove(String cid, String sId);

}
