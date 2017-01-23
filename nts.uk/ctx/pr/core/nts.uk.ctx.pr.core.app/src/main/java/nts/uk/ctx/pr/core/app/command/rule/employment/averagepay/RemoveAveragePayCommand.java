package nts.uk.ctx.pr.core.app.command.rule.employment.averagepay;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@NoArgsConstructor
public class RemoveAveragePayCommand {
	private int attendDayGettingSet;
	private int exceptionPayRate;
	private int roundDigitSet;
	private int roundTimingSet;
}
