package nts.uk.ctx.at.record.app.command.standardtime.operationsetting;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class UpdateAgreementOperationSettingCommand {
	
	private int startingMonth;

	private int numberTimesOverLimitType;

	private int closingDateType;

	private int closingDateAtr;

	private int yearlyWorkTableAtr;

	private int alarmListAtr;
}
