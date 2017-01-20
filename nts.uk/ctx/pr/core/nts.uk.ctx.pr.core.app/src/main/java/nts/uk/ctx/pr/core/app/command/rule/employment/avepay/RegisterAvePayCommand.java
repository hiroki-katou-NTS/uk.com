package nts.uk.ctx.pr.core.app.command.rule.employment.avepay;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@NoArgsConstructor
public class RegisterAvePayCommand {
	private int attendDayGettingSet;
	private int exceptionPayRate;
	private int roundDigitSet;
	private int roundTimingSet;
}
