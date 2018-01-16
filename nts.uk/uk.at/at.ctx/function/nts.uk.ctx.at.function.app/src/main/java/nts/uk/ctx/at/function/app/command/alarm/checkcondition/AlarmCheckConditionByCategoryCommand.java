package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author HungTT
 *
 */

@Data
public class AlarmCheckConditionByCategoryCommand {

	private String code;

	private String name;

	private int category;

	private AlarmCheckTargetConditionCommand targetCondition;

	private List<String> availableRoles;
	
	private DailyAlarmCheckConditionCommand dailyAlarmCheckCondition;
	
	private Schedule4WeekAlarmCheckConditionCommand schedule4WeekAlarmCheckCondition;
	
	private int action;

}
