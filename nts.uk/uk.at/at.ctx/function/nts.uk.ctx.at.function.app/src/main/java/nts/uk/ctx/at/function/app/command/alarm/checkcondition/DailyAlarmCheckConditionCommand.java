package nts.uk.ctx.at.function.app.command.alarm.checkcondition;


import java.util.List;

import lombok.Data;

/**
 * 
 * @author HungTT
 *
 */

@Data
public class DailyAlarmCheckConditionCommand {

	private int conditionToExtractDaily;
	
	private boolean addApplication;
	
	private List<String> listErrorAlarmCode;
	
	private List<String> listExtractConditionWorkRecork;
	
	private List<String> listFixedExtractConditionWorkRecord;
	
}
