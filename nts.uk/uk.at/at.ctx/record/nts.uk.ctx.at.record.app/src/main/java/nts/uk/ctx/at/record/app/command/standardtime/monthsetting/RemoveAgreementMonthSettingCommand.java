package nts.uk.ctx.at.record.app.command.standardtime.monthsetting;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class RemoveAgreementMonthSettingCommand {
	
	private String employeeId;

	private BigDecimal yearMonthValue;

}
