package nts.uk.ctx.at.record.app.command.standardtime.monthsetting;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddAgreementMonthSettingCommand {
	
	private String employeeId;

	private Long yearMonthValue;

	private Long errorOneMonth;

	private Long alarmOneMonth;
}
