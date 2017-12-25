package nts.uk.screen.at.app.dailymodify.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyModifyQuery {

	private String employeeId;
	
	private GeneralDate baseDate;
}
