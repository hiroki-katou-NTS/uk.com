package nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.familycare;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class FamilyCareImport {
	private String familyCareId;
	private String familyId;
	private String sid;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private int careClassifi;
}
