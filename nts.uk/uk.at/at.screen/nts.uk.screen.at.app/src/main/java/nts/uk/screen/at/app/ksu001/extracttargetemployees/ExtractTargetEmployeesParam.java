package nts.uk.screen.at.app.ksu001.extracttargetemployees;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class ExtractTargetEmployeesParam {

	public GeneralDate baseDate;
	public String workplaceId;
	public String workplaceGroupId;
}
