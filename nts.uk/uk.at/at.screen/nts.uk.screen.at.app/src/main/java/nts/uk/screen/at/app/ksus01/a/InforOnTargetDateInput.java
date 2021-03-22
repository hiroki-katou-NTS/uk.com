package nts.uk.screen.at.app.ksus01.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InforOnTargetDateInput {

	private int desiredSubmissionStatus;
	
	private int workHolidayAtr;
	
	private String targetDate;
}
