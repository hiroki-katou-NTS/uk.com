package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author HungTT
 *
 */

@Data
public class AlarmCheckTargetConditionCommand {
	
	private boolean filterByEmployment;

	private boolean filterByClassification;

	private boolean filterByJobTitle;

	private boolean filterByBusinessType;

	private List<String> targetEmployment;

	private List<String> targetClassification;

	private List<String> targetJobTitle;

	private List<String> targetBusinessType;
	
}
