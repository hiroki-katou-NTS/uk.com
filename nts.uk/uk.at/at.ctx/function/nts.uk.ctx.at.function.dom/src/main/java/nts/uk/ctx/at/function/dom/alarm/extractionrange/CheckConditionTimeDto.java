package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckConditionTimeDto {
	private int category;
	private String categoryName;
	private String startDate;
	private String endDate;
	private String startMonth;
	private String endMonth;
	
}
