package nts.uk.ctx.at.record.app.command.standardtime.monthsetting;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class UpdateAgreementMonthSettingCommand {
//	private UpdateParam updateParam;
//	
//	private int yearMonthValueOld;

	private String employeeId;

	private int yearMonthValue;

	private int errorOneMonth;

	private int alarmOneMonth;
	
	private int yearMonthValueOld;
}
