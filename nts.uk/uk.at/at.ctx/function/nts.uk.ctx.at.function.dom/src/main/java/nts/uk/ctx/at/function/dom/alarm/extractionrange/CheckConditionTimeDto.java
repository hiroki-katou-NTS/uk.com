package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckConditionTimeDto {
	private int category;
	private String categoryName;
	private Date startDate;
	private Date endDate;
}
