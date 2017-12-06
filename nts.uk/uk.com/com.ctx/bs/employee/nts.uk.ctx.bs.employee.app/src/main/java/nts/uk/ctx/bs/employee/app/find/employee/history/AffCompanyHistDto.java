package nts.uk.ctx.bs.employee.app.find.employee.history;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Setter
public class AffCompanyHistDto extends PeregDomainDto {
	@PeregItem("IS00020")
	private GeneralDate jobEntryDate;

	@PeregItem("IS00021")
	private GeneralDate retirementDate;
}
