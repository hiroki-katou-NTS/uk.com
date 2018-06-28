package nts.uk.ctx.at.shared.app.command.workingcondition;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class UpdateWorkingCondition2Command extends UpdateWorkingConditionCommand
 {
	
	@PeregRecordId
	private String histId;
	
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
