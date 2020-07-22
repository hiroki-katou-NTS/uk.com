package nts.uk.screen.at.app.ksu001.extracttargetemployees;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class ExtractTargetEmployeesParam {

	public GeneralDate baseDate;
	public String workplaceId;
	public String workplaceGroupId;
}
