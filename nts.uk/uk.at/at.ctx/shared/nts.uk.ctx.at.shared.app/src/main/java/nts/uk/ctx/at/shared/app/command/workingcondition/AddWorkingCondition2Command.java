package nts.uk.ctx.at.shared.app.command.workingcondition;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddWorkingCondition2Command extends AddWorkingConditionCommand{
	
	@PeregEmployeeId
	private String employeeId;
	
	/**
	 * 期間
	 */
	@PeregItem("IS00780")
	private String period;

	/**
	 * 開始日
	 */
	@PeregItem("IS00781")
	private GeneralDate startDate;

	/**
	 * 終了日
	 */
	@PeregItem("IS00782")
	private GeneralDate endDate;

}
