/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.imports.dto;

import lombok.Data;

/**
 * The Class LaborInsuranceOfficeImportDto.
 */
@Data
public class LaborInsuranceOfficeImportDto {

	/** The social insurance office import. */
	private SocialInsuranceOfficeImportDto socialInsuranceOfficeImport;

	/** The check update duplicate code. */
	private int checkUpdateDuplicateCode; // 0 update //1 none update
}
