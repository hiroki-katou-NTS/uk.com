package nts.uk.ctx.bs.employee.pub.familyrelatedinformation.familycare;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class FamilyCareExport {
	private String familyCareId;
	private String familyId;
	private String sid;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private int careClassifi;
}
