/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.employee.find;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.record.app.find.workrecord.monthcal.employee.ShaMonthCalSetFinder;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew.ShainRegularWorkHourDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew.ShainStatWorkTimeSetFinder;

/**
 * The Class Kmk004ShaDtoFinder.
 */
@Stateless
@Transactional
public class Kmk004ShaDtoFinder {

	/** The com stat work time set finder. */
	@Inject
	private ShainStatWorkTimeSetFinder shaStatWorkTimeSetFinder;

	/** The sha month cal set finder. */
	@Inject
	private ShaMonthCalSetFinder shaMonthCalSetFinder;

	/**
	 * Find kmk 004 dto.
	 *
	 * @param year
	 *            the year
	 * @return the kmk 004 com dto
	 */
	public Kmk004ShaDto findKmk004ShaDto(Integer year, String sid) {
		Kmk004ShaDto kmk004Dto = new Kmk004ShaDto();
		
		kmk004Dto.setStatWorkTimeSetDto(shaStatWorkTimeSetFinder.getDetails(year, sid));
		kmk004Dto.setMonthCalSetDto(shaMonthCalSetFinder.getDetails(sid));
		
		return kmk004Dto;
	}
	

	/**
	 * Find all shain reg labor time.
	 *
	 * @return the list
	 */
	public List<ShainRegularWorkHourDto> findAllShainRegLaborTime(){		
		return this.shaStatWorkTimeSetFinder.findAllShainRegLaborTime();
	}
}
