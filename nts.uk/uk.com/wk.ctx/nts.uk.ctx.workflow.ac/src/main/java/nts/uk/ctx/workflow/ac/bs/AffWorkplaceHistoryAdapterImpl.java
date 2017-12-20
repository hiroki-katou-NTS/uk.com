/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.workflow.ac.bs;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.AffWorkplaceHistoryAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.AffWorkplaceHistoryImport;

/**
 * The Class AffWorkplaceHistoryAdapterImpl.
 */
public class AffWorkplaceHistoryAdapterImpl implements AffWorkplaceHistoryAdapter{

	@Override
	public Optional<AffWorkplaceHistoryImport> findByBaseDate(String employeeId,
			GeneralDate baseDate) {
		
		return Optional.empty();
	}

}
