/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.company.find;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.record.app.find.workrecord.monthcal.company.ComMonthCalSetFinder;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew.ComStatWorkTimeSetFinder;

/**
 * The Class Kmk004ComDtoFinder.
 */
@Stateless
@Transactional
public class Kmk004ComDtoFinder {

	/** The com stat work time set finder. */
	@Inject
	private ComStatWorkTimeSetFinder comStatWorkTimeSetFinder;

	/** The com month cal set finder. */
	@Inject
	private ComMonthCalSetFinder comMonthCalSetFinder;

	/**
	 * Find kmk 004 dto.
	 *
	 * @param year
	 *            the year
	 * @return the kmk 004 com dto
	 */
	public Kmk004ComDto findKmk004Dto(Integer year) {
		Kmk004ComDto kmk004Dto = new Kmk004ComDto();
		kmk004Dto.setMonthCalSetDto(comMonthCalSetFinder.getDetails());
		kmk004Dto.setStatWorkTimeSetDto(comStatWorkTimeSetFinder.getDetails(year));
		return kmk004Dto;
	}

}
