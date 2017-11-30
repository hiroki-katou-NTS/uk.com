/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.pred;

import nts.uk.ctx.at.shared.app.find.pred.dto.PredDto;

/**
 * The Class PredFinder.
 */
//@Stateless
public class PredFinder {

	/** The predetemine time set repository. */
//	@Inject
//	private PredetemineTimeSetRepository predetemineTimeSetRepository;

	/**
	 * Find by code.
	 *
	 * @param workTimeCode the work time code
	 * @return the pred dto
	 */
	public PredDto findByCode(String workTimeCode) {
//		String companyId = AppContexts.user().companyId();
//		PredetemineTimeSet pre = this.predetemineTimeSetRepository.findByCode(companyId, workTimeCode);
		PredDto dto = new PredDto();
//		pre.saveToMemento(dto);
		return dto;
	}

}
