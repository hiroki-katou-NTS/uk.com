package nts.uk.screen.at.app.dailyperformance.correction.dto.workplacehist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlaceHistTemp {
	
	private String employeeId;
	
	private String workplaceId;
	
	private GeneralDate startDate;
	
	private GeneralDate endDate;
}
