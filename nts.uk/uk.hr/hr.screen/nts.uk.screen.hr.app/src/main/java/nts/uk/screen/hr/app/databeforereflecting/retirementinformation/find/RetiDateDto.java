package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RetiDateDto {
	
	DateDisplaySettingPeriodDto dateDisplaySettingPeriod;
	
	List<RetirementCourseDto>  retirementCourses;
	
	List<ReferEvaluationItemDto> referEvaluationItems;
}
