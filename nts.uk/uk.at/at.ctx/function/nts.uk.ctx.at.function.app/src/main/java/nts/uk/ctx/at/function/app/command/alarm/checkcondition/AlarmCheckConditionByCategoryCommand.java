package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.DeleteAgreeCondOtCommand;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.DeleteAgreeConditionErrorCommand;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.UpdateAgree36Command;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.ApprovalAlarmCheckConDto;

/**
 * 
 * @author HungTT
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class AlarmCheckConditionByCategoryCommand {

	private String code;

	private String name;

	private int category;

	private AlarmCheckTargetConditionCommand targetCondition;

	private List<String> availableRoles;
	
	private DailyAlarmCheckConditionCommand dailyAlarmCheckCondition;
	
	private ApprovalAlarmCheckConDto approvalAlarmCheckConDto;
	
	private Schedule4WeekAlarmCheckConditionCommand schedule4WeekAlarmCheckCondition;
	
	private int action;

	private MonAlarmCheckConCommand monAlarmCheckCon;
	
	private UpdateAgree36Command condAgree36; 
	
	List<DeleteAgreeConditionErrorCommand> deleteCondError;
	
	List<DeleteAgreeCondOtCommand> deleteCondOt;
	
	private MulMonCheckCondCommand mulMonCheckCond;
	
	private AnnualHolidayAlarmConditionCommand annualHolidayAlCon;
	
	private MasterCheckAlarmCheckConditionCommand masterCheckAlarmCheckCondition;
	
	public AlarmCheckConditionByCategoryCommand(String code, String name, int category,
			AlarmCheckTargetConditionCommand targetCondition, List<String> availableRoles,
			DailyAlarmCheckConditionCommand dailyAlarmCheckCondition,
			ApprovalAlarmCheckConDto approvalAlarmCheckConDto,
			Schedule4WeekAlarmCheckConditionCommand schedule4WeekAlarmCheckCondition, int action,
			MonAlarmCheckConCommand monAlarmCheckCon,
			UpdateAgree36Command agree36, MulMonCheckCondCommand mulMonCheckCond, 
			AnnualHolidayAlarmConditionCommand annualHolidayAlCon, MasterCheckAlarmCheckConditionCommand masterCheckAlarmCheckCondition) {
		super();
		this.code = code;
		this.name = name;
		this.category = category;
		this.targetCondition = targetCondition;
		this.availableRoles = availableRoles;
		this.dailyAlarmCheckCondition = dailyAlarmCheckCondition;
		this.approvalAlarmCheckConDto = approvalAlarmCheckConDto;
		this.schedule4WeekAlarmCheckCondition = schedule4WeekAlarmCheckCondition;
		this.action = action;
		this.monAlarmCheckCon = monAlarmCheckCon;
		this.condAgree36 = agree36;
		this.mulMonCheckCond = mulMonCheckCond;
		this.annualHolidayAlCon = annualHolidayAlCon;
		this.masterCheckAlarmCheckCondition = masterCheckAlarmCheckCondition;
	}

}
