/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.imports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.imports.dto.LaborInsuranceOfficeCheckImportDto;
import nts.uk.ctx.pr.core.app.insurance.labor.imports.dto.LaborInsuranceOfficeImportDto;
import nts.uk.ctx.pr.core.app.insurance.labor.imports.dto.LaborInsuranceOfficeImportOutDto;
import nts.uk.ctx.pr.core.app.insurance.labor.imports.dto.SocialInsuranceOfficeImportDto;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AddLaborInsuranceOfficeCommand.
 */
@Stateless
public class LaborInsuranceOfficeImporter implements Serializable {

	/** The labor insurance office repository. */
	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOfficeRepository;

	/**
	 * To domain.
	 *
	 * @return the labor insurance office
	 */
	public LaborInsuranceOfficeCheckImportDto checkDuplicateCode(
			List<SocialInsuranceOfficeImportDto> lstSocialInsuranceOfficeImport) {
		String companyCode = AppContexts.user().companyCode();
		List<LaborInsuranceOffice> lstLaborInsuranceOffice = new ArrayList<>();
		LaborInsuranceOfficeCheckImportDto laborInsuranceOfficeCheckImport = new LaborInsuranceOfficeCheckImportDto();
		laborInsuranceOfficeCheckImport.setCode("0");
		laborInsuranceOfficeCheckImport.setMessage("SUCC");
		for (SocialInsuranceOfficeImportDto socialInsuranceOfficeImportDto : lstSocialInsuranceOfficeImport) {
			if (laborInsuranceOfficeRepository.isDuplicateCode(new CompanyCode(companyCode),
					new OfficeCode(socialInsuranceOfficeImportDto.getCode()))) {
				laborInsuranceOfficeCheckImport.setCode("1");
				laborInsuranceOfficeCheckImport.setMessage("FAIL");
			}
		}
		return laborInsuranceOfficeCheckImport;
	}

	public LaborInsuranceOfficeImportOutDto importData(LaborInsuranceOfficeImportDto laborInsuranceOfficeImportDto) {
		String companyCode = AppContexts.user().companyCode();
		LaborInsuranceOfficeImportOutDto laborInsuranceOfficeImportOutDto = new LaborInsuranceOfficeImportOutDto();
		int totalImport = 0;
		laborInsuranceOfficeImportOutDto.setCode("0");
		for (SocialInsuranceOfficeImportDto socialInsuranceOfficeImportDto : laborInsuranceOfficeImportDto
				.getLstSocialInsuranceOfficeImport()) {
			if (laborInsuranceOfficeRepository.isDuplicateCode(new CompanyCode(companyCode),
					new OfficeCode(socialInsuranceOfficeImportDto.getCode()))) {
				if (laborInsuranceOfficeImportDto.getCheckUpdateDuplicateCode() == 0) {
					laborInsuranceOfficeRepository.update(socialInsuranceOfficeImportDto.toDomain(companyCode));
					totalImport++;
				}
			} else {
				laborInsuranceOfficeRepository.add(socialInsuranceOfficeImportDto.toDomain(companyCode));
				totalImport++;
			}
			
		}
		laborInsuranceOfficeImportOutDto.setTotalImport(totalImport);
		return laborInsuranceOfficeImportOutDto;
	}

}
