package nts.uk.ctx.pr.core.app.find.rule.employment.avepay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@AllArgsConstructor
public class AvePayDto {
	private int attendDayGettingSet;
	private int exceptionPayRate;
	private int roundDigitSet;
	private int roundTimingSet;
}
