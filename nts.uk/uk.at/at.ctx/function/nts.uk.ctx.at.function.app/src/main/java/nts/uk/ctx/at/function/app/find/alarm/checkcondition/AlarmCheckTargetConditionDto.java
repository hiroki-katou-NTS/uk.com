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
public class AlarmCheckTargetConditionDto {
	
	private String targetConditionId;
	
	private boolean filterByEmployment;

	private boolean filterByClassification;

	private boolean filterByJobTitle;

	private boolean filterByBusinessType;

	private List<String> targetEmployment;

	private List<String> targetClassification;

	private List<String> targetJobTitle;

	private List<String> targetBusinessType;
	
}
