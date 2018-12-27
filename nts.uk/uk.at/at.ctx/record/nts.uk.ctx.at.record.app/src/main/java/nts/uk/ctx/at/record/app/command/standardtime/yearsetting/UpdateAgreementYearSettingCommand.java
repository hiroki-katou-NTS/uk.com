package nts.uk.ctx.at.record.app.command.standardtime.yearsetting;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class UpdateAgreementYearSettingCommand {

	private String employeeId;

	private int yearValue;
	
	private int errorOneYear;
	
	private int alarmOneYear;
	
	private int yearMonthValueOld;
}
