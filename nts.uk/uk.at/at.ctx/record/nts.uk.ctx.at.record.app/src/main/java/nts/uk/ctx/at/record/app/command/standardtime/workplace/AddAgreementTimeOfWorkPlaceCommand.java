package nts.uk.ctx.at.record.app.command.standardtime.workplace;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddAgreementTimeOfWorkPlaceCommand {
	
	private String workPlaceId;
	
	private int laborSystemAtr;

	private BigDecimal alarmWeek;

	private BigDecimal errorWeek;

	private BigDecimal alarmTwoWeeks;

	private BigDecimal errorTwoWeeks;

	private BigDecimal alarmFourWeeks;

	private BigDecimal errorFourWeeks;

	private BigDecimal alarmOneMonth;

	private BigDecimal errorOneMonth;

	private BigDecimal alarmTwoMonths;

	private BigDecimal errorTwoMonths;

	private BigDecimal alarmThreeMonths;

	private BigDecimal errorThreeMonths;

	private BigDecimal alarmOneYear;

	private BigDecimal errorOneYear;
}
