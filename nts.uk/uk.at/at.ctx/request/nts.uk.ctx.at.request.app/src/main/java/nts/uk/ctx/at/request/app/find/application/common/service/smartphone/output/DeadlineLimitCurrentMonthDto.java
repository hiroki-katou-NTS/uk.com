package nts.uk.ctx.at.request.app.find.application.common.service.smartphone.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.DeadlineLimitCurrentMonth;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class DeadlineLimitCurrentMonthDto {
	/**
	 * 利用区分
	 */
	private boolean useAtr;
	
	/**
	 * 申請締切日
	 */
	private String opAppDeadline;
	
	public static DeadlineLimitCurrentMonthDto fromDomain(DeadlineLimitCurrentMonth deadlineLimitCurrentMonth) {
		return new DeadlineLimitCurrentMonthDto(
				deadlineLimitCurrentMonth.isUseAtr(), 
				deadlineLimitCurrentMonth.getOpAppDeadline().map(x -> x.toString()).orElse(null));
	}
}
