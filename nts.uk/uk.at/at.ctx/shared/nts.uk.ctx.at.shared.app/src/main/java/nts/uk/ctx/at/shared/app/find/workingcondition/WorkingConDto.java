package nts.uk.ctx.at.shared.app.find.workingcondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @author quytb
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WorkingConDto {
	private String histId;
	private String period;
	
	public static WorkingConDto toWorkingConDto(String id, String startDate, String endDate) {
		String period = startDate + " ~ " + endDate;
		return new WorkingConDto(id, period);
	}
}
