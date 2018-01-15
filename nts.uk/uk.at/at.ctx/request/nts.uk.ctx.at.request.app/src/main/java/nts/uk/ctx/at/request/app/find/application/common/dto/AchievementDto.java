package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class AchievementDto {

	private GeneralDate date;
	
	private WorkTypeDto workType;
	
	private WorkTimeDto workTime;
	
	private Integer startTime1;
	
	private Integer endTime1;
	
	private Integer startTime2;
	
	private Integer endTime2;
	
	public static AchievementDto convertFromAchievementOutput(AchievementOutput achievementOutput){
		return new AchievementDto(
				achievementOutput.getDate(), 
				WorkTypeDto.convertFromWorkTypeOutput(achievementOutput.getWorkType()), 
				WorkTimeDto.convertFromWorkTimeOutput(achievementOutput.getWorkTime()), 
				achievementOutput.getStartTime1(), 
				achievementOutput.getEndTime1(), 
				achievementOutput.getStartTime2(), 
				achievementOutput.getEndTime2());
	}
	
}
