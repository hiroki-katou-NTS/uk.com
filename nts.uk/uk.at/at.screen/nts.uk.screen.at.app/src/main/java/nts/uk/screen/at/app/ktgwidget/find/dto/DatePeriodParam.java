package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
@AllArgsConstructor
public class DatePeriodParam {

	public String code;
	
	public GeneralDate strMonth;
	
	public GeneralDate endMonth;
}
