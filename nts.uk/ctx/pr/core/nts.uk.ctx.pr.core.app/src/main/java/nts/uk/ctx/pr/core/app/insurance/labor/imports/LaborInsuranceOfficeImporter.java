/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.imports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.insurance.labor.imports.dto.LaborInsuranceOfficeCheckImportDto;
import nts.uk.ctx.pr.core.app.insurance.labor.imports.dto.LaborInsuranceOfficeImportDto;
import nts.uk.ctx.pr.core.app.insurance.labor.imports.dto.LaborInsuranceOfficeImportOutDto;
import nts.uk.ctx.pr.core.app.insurance.labor.imports.dto.SocialInsuranceOfficeImportDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.service.LaborInsuranceOfficeService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AddLaborInsuranceOfficeCommand.
 */
@Stateless
public class LaborInsuranceOfficeImporter implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The labor insurance office repository. */
	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOfficeRepository;

	/** The labor insurance office service. */
	@Inject
	private LaborInsuranceOfficeService laborInsuranceOfficeService;

	/**
	 * To domain.
	 *
	 * @return the labor insurance office
	 */
	public LaborInsuranceOfficeCheckImportDto checkDuplicateCode(
		SocialInsuranceOfficeImportDto socialInsuranceOfficeImport) {

		// get user login companyCode
		String companyCode = AppContexts.user().companyCode();
		List<LaborInsuranceOffice> lstLaborInsuranceOffice = new ArrayList<>();
		LaborInsuranceOfficeCheckImportDto laborInsuranceOfficeCheckImport;
		laborInsuranceOfficeCheckImport = new LaborInsuranceOfficeCheckImportDto();
		laborInsuranceOfficeCheckImport.setCode("0");
		laborInsuranceOfficeCheckImport.setMessage("SUCC");

		// check exist
		Optional<LaborInsuranceOffice> optionalCheck = this.laborInsuranceOfficeRepository
			.findById(companyCode, socialInsuranceOfficeImport.getCode());

		if (optionalCheck.isPresent()) {
			laborInsuranceOfficeCheckImport.setCode("1");
			laborInsuranceOfficeCheckImport.setMessage("FAIL");
		}

		return laborInsuranceOfficeCheckImport;
	}

	public LaborInsuranceOfficeImportOutDto importData(
		LaborInsuranceOfficeImportDto laborInsuranceOfficeImportDto) {

		// get userlogin companyCode
		String companyCode = AppContexts.user().companyCode();
		LaborInsuranceOfficeImportOutDto laborInsuranceOfficeImportOutDto;
		laborInsuranceOfficeImportOutDto = new LaborInsuranceOfficeImportOutDto();
		int totalImport = 0;
		laborInsuranceOfficeImportOutDto.setCode("0");

		// check exist
		Optional<LaborInsuranceOffice> optionalCheck = this.laborInsuranceOfficeRepository
			.findById(companyCode, laborInsuranceOfficeImportDto.getSocialInsuranceOfficeImport().getCode());

		if (optionalCheck.isPresent()) {
			if (laborInsuranceOfficeImportDto.getCheckUpdateDuplicateCode() == 0) {
				// update
				laborInsuranceOfficeRepository.update(
					laborInsuranceOfficeImportDto.getSocialInsuranceOfficeImport().toDomain(companyCode));
				totalImport++;
			}
		} else {
			// add new
			laborInsuranceOfficeRepository
				.add(laborInsuranceOfficeImportDto.getSocialInsuranceOfficeImport().toDomain(companyCode));
			totalImport++;
		}
		laborInsuranceOfficeImportOutDto.setTotalImport(totalImport);

		return laborInsuranceOfficeImportOutDto;
	}

}
