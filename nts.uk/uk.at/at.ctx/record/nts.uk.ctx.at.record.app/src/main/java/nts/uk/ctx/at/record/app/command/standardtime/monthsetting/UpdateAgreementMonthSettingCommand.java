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
	
	private String employeeId;

	private int yearMonthValue;

	private int errorOneMonth;

	private int alarmOneMonth;
}
