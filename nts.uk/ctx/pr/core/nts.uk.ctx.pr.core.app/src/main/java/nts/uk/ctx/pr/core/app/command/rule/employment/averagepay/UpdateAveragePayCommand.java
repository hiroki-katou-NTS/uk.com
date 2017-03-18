package nts.uk.ctx.pr.core.app.command.rule.employment.averagepay;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@NoArgsConstructor
public class UpdateAveragePayCommand {
	private int attendDayGettingSet;
	private int exceptionPayRate;
	private int roundDigitSet;
	private int roundTimingSet;
	private List<String> salarySelectedCode;
	private List<String> attendSelectedCode;
}

