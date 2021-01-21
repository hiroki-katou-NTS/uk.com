package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha;

import java.util.Optional;

/**
 * The Interface EmployeeLaborDeforSetTemporaryRepository.
 */
public interface ShaDeforLaborMonthActCalSetRepo {

	/**
	 * Find emp labor defor set temp by cid and emp id.
	 *
	 * @param cId the cid
	 * @param sId the emp id
	 * @return the optional
	 */
	Optional<ShaDeforLaborMonthActCalSet> find(String cId, String sId);

	/**
	 * Adds the.
	 *
	 * @param laborDeforSetTemp the emp labor defor set temp
	 */
	void add(ShaDeforLaborMonthActCalSet laborDeforSetTemp);

	/**
	 * Update.
	 *
	 * @param laborDeforSetTemp the emp labor defor set temp
	 */
	void update(ShaDeforLaborMonthActCalSet laborDeforSetTemp);

	/**
	 * Delete.
	 *
	 * @param laborDeforSetTemp the emp labor defor set temp
	 */
	void remove(String cId, String sId);

}
