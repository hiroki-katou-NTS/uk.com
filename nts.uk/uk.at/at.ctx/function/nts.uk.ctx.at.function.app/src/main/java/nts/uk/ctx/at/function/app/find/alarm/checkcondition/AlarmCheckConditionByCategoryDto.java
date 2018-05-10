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
public class AlarmCheckConditionByCategoryDto {

	private String code;

	private String name;

	private int category;

	private AlarmCheckTargetConditionDto targetCondition;

	private List<String> availableRoles;

	private int schedule4WeekCondition;

	private DailyAlarmCheckConditionDto dailyAlarmCheckCondition;

	private MonAlarmCheckConDto monAlarmCheckConDto;
}
