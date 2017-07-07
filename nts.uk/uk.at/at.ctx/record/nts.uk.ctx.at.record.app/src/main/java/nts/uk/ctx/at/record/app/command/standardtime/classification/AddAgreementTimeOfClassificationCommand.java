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
	
	private String classificationCode;
	
	private int laborSystemAtr;

	private Long alarmWeek;

	private Long errorWeek;

	private Long limitWeek;

	private Long alarmTwoWeeks;

	private Long errorTwoWeeks;

	private Long limitTwoWeeks;

	private Long alarmFourWeeks;

	private Long errorFourWeeks;

	private Long limitFourWeeks;

	private Long alarmOneMonth;

	private Long errorOneMonth;

	private Long limitOneMonth;

	private Long alarmTwoMonths;

	private Long errorTwoMonths;

	private Long limitTwoMonths;

	private Long alarmThreeMonths;

	private Long errorThreeMonths;

	private Long limitThreeMonths;

	private Long alarmOneYear;

	private Long errorOneYear;

	private Long limitOneYear;
}
