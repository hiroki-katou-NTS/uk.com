/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.find;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.app.salarydetail.find.dto.SalaryOutputSettingDto;
import nts.uk.ctx.pr.report.app.salarydetail.find.dto.SalaryOutputSettingHeadDto;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingRepository;

/**
 * The Class SalaryOutputSettingFinder.
 */
@Stateless
public class SalaryOutputSettingFinder {

	/** The repository. */
	@Inject
	private SalaryOutputSettingRepository repository;

	/**
	 * Find.
	 *
	 * @param code the code
	 * @return the salary output setting dto
	 */
	public SalaryOutputSettingDto find(String code) {
		// TODO ...
		SalaryOutputSettingDto dto = SalaryOutputSettingDto.builder().build();
		return dto;
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<SalaryOutputSettingHeadDto> findAll() {
		// TODO ...
		List<SalaryOutputSettingHeadDto> list = new ArrayList<SalaryOutputSettingHeadDto>();
		return list;
	}
}
