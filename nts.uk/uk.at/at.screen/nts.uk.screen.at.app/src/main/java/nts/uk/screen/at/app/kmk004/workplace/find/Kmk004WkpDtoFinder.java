/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.workplace.find;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.record.app.find.workrecord.monthcal.workplace.WkpMonthCalSetFinder;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew.WkpRegularWorkHourDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew.WkpStatWorkTimeSetFinder;

/**
 * The Class Kmk004WkpDtoFinder.
 */
@Stateless
@Transactional
public class Kmk004WkpDtoFinder {

	/** The emp stat work time set finder. */
	@Inject
	private WkpStatWorkTimeSetFinder wkpStatWorkTimeSetFinder;

	/** The emp month cal set finder. */
	@Inject
	private WkpMonthCalSetFinder wkpMonthCalSetFinder;

	/**
	 * Find kmk 004 wkp dto.
	 *
	 * @param year
	 *            the year
	 * @param wkpId
	 *            the wkp id
	 * @return the kmk 004 wkp dto
	 */
	public Kmk004WkpDto findKmk004WkpDto(Integer year, String wkpId) {
		Kmk004WkpDto kmk004Dto = new Kmk004WkpDto();

		kmk004Dto.setStatWorkTimeSetDto(wkpStatWorkTimeSetFinder.getDetails(year, wkpId));
		kmk004Dto.setMonthCalSetDto(wkpMonthCalSetFinder.getDetails(wkpId));

		return kmk004Dto;
	}
	

	/**
	 * Find all wkp reg work hour dto.
	 *
	 * @return the list
	 */
	public List<WkpRegularWorkHourDto> findAllWkpRegWorkHourDto(){		
		return this.wkpStatWorkTimeSetFinder.findAllWkpRegWorkHourDto();
	}

}
