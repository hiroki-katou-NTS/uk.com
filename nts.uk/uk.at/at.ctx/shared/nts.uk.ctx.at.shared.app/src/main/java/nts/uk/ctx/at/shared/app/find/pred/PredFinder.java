/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.pred;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.pred.dto.PredDto;
import nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSet;
import nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PredFinder.
 */
@Stateless
public class PredFinder {

	/** The predetemine time set repository. */
	@Inject
	private PredetemineTimeSetRepository predetemineTimeSetRepository;

	/**
	 * Find by code.
	 *
	 * @param workTimeCode the work time code
	 * @return the pred dto
	 */
	public PredDto findByCode(String workTimeCode) {
		String companyId = AppContexts.user().companyId();
		PredetemineTimeSet pre = this.predetemineTimeSetRepository.findByCode(companyId, workTimeCode);
		PredDto dto = new PredDto();
		pre.saveToMemento(dto);
		return dto;
	}

}
