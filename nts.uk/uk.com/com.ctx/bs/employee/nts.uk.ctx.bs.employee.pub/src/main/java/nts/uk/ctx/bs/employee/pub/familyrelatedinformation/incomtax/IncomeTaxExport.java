package nts.uk.ctx.bs.employee.pub.familyrelatedinformation.incomtax;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class IncomeTaxExport {
	private String IncomeTaxID;
	private String familyMemberId;
	private String sid;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private boolean supporter;
	private int disabilityType;
	private int deductionTargetType;
}
