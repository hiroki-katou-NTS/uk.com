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
public class AddAgreementTimeOfWorkPlaceCommand {
	
	private String workPlaceId;
	
	private int laborSystemAtr;

	private BigDecimal alarmWeek;

	private BigDecimal errorWeek;

	private BigDecimal limitWeek;

	private BigDecimal alarmTwoWeeks;

	private BigDecimal errorTwoWeeks;

	private BigDecimal limitTwoWeeks;

	private BigDecimal alarmFourWeeks;

	private BigDecimal errorFourWeeks;

	private BigDecimal limitFourWeeks;

	private BigDecimal alarmOneMonth;

	private BigDecimal errorOneMonth;

	private BigDecimal limitOneMonth;

	private BigDecimal alarmTwoMonths;

	private BigDecimal errorTwoMonths;

	private BigDecimal limitTwoMonths;

	private BigDecimal alarmThreeMonths;

	private BigDecimal errorThreeMonths;

	private BigDecimal limitThreeMonths;

	private BigDecimal alarmOneYear;

	private BigDecimal errorOneYear;

	private BigDecimal limitOneYear;
}
