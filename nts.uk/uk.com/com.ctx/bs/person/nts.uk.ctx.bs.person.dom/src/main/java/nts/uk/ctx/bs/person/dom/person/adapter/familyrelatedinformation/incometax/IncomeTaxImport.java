package nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.incometax;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class IncomeTaxImport {
	private String IncomeTaxID;
	private String familyMemberId;
	private String sid;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private boolean supporter;
	private int disabilityType;
	private int deductionTargetType;
}
