package nts.uk.ctx.bs.employee.pub.familyrelatedinformation.familysocialinsurance;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class FamilySocialInsuranceExport {
	private String familyMemberId;
	private String sid;
	private String socialInsuaranceId;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private boolean nursingCare;
	private boolean healthInsuranceDependent;
	private boolean nationalPensionNo3;
	private String basicPensionNumber;
}
