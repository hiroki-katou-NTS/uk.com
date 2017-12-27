package nts.uk.ctx.at.function.app.find.alarm;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckConditionDto {
	private int alarmCategory;
	private List<String> checkConditionCodes;	
}
