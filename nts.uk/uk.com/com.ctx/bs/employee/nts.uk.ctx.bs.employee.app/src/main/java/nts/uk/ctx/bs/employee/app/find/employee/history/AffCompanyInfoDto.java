package nts.uk.ctx.bs.employee.app.find.employee.history;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class AffCompanyInfoDto {
	private String historyId;
	private String recruitmentClassification;

	private GeneralDate adoptionDate;
	private GeneralDate retirementAllowanceCalcStartDate;
}
