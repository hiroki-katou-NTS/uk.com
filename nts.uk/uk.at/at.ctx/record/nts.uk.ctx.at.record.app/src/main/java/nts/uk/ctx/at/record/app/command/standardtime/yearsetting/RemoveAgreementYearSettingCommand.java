package nts.uk.ctx.at.record.app.command.standardtime.yearsetting;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class RemoveAgreementYearSettingCommand {

	private String employeeId;

	private int yearValue;
}
