package nts.uk.ctx.at.shared.dom.workrule.workform;

import java.util.Optional;

public interface FlexWorkMntSetRepository {
	
	/**
	 * Find by company Id
	 * @param companyId
	 * @return
	 */
	Optional<FlexWorkSet> find (String companyId);
	
	/**
	 * Add new flex work management setting
	 * @param setting
	 */
	void add(FlexWorkSet setting);
	
	/**
	 * Update new flex work management setting
	 * @param setting
	 */
	void update(FlexWorkSet setting);
	
}
