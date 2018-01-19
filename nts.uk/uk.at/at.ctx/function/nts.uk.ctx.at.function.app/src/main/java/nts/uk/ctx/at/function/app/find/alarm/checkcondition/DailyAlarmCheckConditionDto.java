package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 
 * @author HungTT
 *
 */

@Data
@AllArgsConstructor
public class DailyAlarmCheckConditionDto {

	private boolean addApplication;
	
	private int conditionToExtractDaily;
	
	private List<String> listErrorAlarmCode;
	
	private List<String> listExtractConditionWorkRecork;
	
	private List<FixedConditionWorkRecordDto> listFixedExtractConditionWorkRecord;
	
}
