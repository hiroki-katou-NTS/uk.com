package nts.uk.ctx.pr.core.app.insurance.labor.imports.dto;

import java.util.List;

import lombok.Data;

@Data
public class LaborInsuranceOfficeImportDto {

	private List<SocialInsuranceOfficeImportDto> lstSocialInsuranceOfficeImport;
	
	private int checkUpdateDuplicateCode; //0 update //1 none update
}
