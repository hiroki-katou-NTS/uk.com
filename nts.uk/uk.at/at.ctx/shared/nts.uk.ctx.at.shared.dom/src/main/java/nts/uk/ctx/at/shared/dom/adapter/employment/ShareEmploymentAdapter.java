package nts.uk.ctx.at.shared.dom.adapter.employment;

import java.util.List;


public interface ShareEmploymentAdapter {

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	// RequestList #89
	List<EmpCdNameImport> findAll(String companyId);
}
