/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.employment.find;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.record.app.find.workrecord.monthcal.employment.EmpMonthCalSetFinder;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew.EmpRegularWorkHourDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew.EmpStatWorkTimeSetFinder;

/**
 * The Class Kmk004EmpDtoFinder.
 */
@Stateless
@Transactional
public class Kmk004EmpDtoFinder {

	/** The emp stat work time set finder. */
	@Inject
	private EmpStatWorkTimeSetFinder empStatWorkTimeSetFinder;

	/** The emp month cal set finder. */
	@Inject
	private EmpMonthCalSetFinder empMonthCalSetFinder;

	/**
	 * Find kmk 004 emp dto.
	 *
	 * @param year
	 *            the year
	 * @param empCode
	 *            the emp code
	 * @return the kmk 004 emp dto
	 */
	public Kmk004EmpDto findKmk004EmpDto(Integer year, String empCode) {
		Kmk004EmpDto kmk004Dto = new Kmk004EmpDto();

		kmk004Dto.setStatWorkTimeSetDto(empStatWorkTimeSetFinder.getDetails(year, empCode));
		kmk004Dto.setMonthCalSetDto(empMonthCalSetFinder.getDetails(empCode));

		return kmk004Dto;
	}
	

	/**
	 * Find all emp regular work hour.
	 *
	 * @return the list
	 */
	public List<EmpRegularWorkHourDto> findAllEmpRegWorkHour(){
		return this.empStatWorkTimeSetFinder.findAllEmpRegWorkHour();
		
	}
}
