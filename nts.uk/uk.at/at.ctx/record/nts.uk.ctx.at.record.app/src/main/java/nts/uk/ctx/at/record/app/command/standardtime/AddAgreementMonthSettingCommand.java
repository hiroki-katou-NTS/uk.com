package nts.uk.ctx.at.record.app.command.standardtime;

import java.math.BigDecimal;

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

	private BigDecimal yearMonthValue;

	private BigDecimal errorOneMonth;

	private BigDecimal alarmOneMonth;
}
