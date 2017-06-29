package nts.uk.ctx.at.record.app.command.standardtime.classification;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddAgreementTimeOfClassificationCommand {
	
	private String employmentCategoryCode;
	
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
