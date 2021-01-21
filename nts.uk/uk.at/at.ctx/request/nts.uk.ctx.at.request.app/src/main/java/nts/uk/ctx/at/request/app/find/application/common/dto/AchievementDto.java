package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
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
/**
 * 表示する実績内容
 */
@Data
public class AchievementDto {

	private String date;
	
	private WorkTypeDto workType;
	
	private WorkTimeDto workTime;
	
	private Integer startTime1;
	
	private Integer endTime1;
	
	private Integer startTime2;
	
	private Integer endTime2;
	
	public static AchievementDto convertFromAchievementOutput(AchievementOutput achievementOutput){
		return new AchievementDto(
				achievementOutput.getDate().toString("yyyy/MM/dd"), 
				WorkTypeDto.convertFromWorkTypeOutput(achievementOutput.getWorkType()), 
				WorkTimeDto.convertFromWorkTimeOutput(achievementOutput.getWorkTime()), 
				achievementOutput.getStartTime1(), 
				achievementOutput.getEndTime1(), 
				achievementOutput.getStartTime2(), 
				achievementOutput.getEndTime2());
	}
	
	public AchievementOutput toDomain() {
		return new AchievementOutput(
				GeneralDate.fromString(date, "yyyy/MM/dd"), 
				workType.toDomain(), 
				workTime.toDomain(), 
				startTime1, 
				endTime1, 
				startTime2, 
				endTime2);
	}
	
}
