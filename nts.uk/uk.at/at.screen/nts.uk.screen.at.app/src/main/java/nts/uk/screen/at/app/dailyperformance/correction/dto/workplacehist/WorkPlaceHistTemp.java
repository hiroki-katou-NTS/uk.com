package nts.uk.screen.at.app.dailyperformance.correction.dto.workplacehist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlaceHistTemp {
	
	private String employeeId;
	
	private String workplaceId;
	
    private String name;

	public WorkPlaceHistTemp(String employeeId, String workplaceId) {
		this.employeeId = employeeId;
		this.workplaceId = workplaceId;
	}
    
    
}
