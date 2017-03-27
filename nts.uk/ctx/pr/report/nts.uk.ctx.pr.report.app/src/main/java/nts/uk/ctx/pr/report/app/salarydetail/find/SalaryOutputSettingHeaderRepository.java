/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.find;

import java.util.List;

import nts.uk.ctx.pr.report.app.salarydetail.find.dto.SalaryOutputSettingHeaderDto;


/**
 * The Interface SalaryOutputSettingHeadRepository.
 */
public interface SalaryOutputSettingHeaderRepository {
	
	/**
	 * Find output setting heads.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<SalaryOutputSettingHeaderDto> findAll(String companyCode);
}
