package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class AchievementOutput {
	
	private GeneralDate date;
	
	private WorkTypeOutput workType;
	
	private WorkTimeOutput workTime;
	
	private Integer startTime1;
	
	private Integer endTime1;
	
	private Integer startTime2;
	
	private Integer endTime2;
	
}
