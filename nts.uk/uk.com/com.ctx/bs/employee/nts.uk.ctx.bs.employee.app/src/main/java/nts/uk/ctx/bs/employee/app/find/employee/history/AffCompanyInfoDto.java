package nts.uk.ctx.bs.employee.app.find.employee.history;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Setter
public class AffCompanyInfoDto extends PeregDomainDto {

	@PeregItem("IS00022")
	private GeneralDate adoptionDate;

	@PeregItem("IS00023")
	private String recruitmentClassification;
	
	@PeregItem("IS00024")
	private GeneralDate retirementAllowanceCalcStartDate;
}
