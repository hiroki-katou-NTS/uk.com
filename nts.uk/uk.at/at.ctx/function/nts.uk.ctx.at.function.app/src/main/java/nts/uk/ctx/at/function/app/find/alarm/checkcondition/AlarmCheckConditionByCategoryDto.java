package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.ConExtractedDaily;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff.AlarmCheckCondition4W4D;

/**
 * 
 * @author HungTT
 *
 */

@Data
@AllArgsConstructor
public class AlarmCheckConditionByCategoryDto {

	private String code;

	private String name;

	private int category;

	private AlarmCheckTargetConditionDto targetCondition;

	private List<String> availableRoles;

	private int schedule4WeekCondition;

	private DailyAlarmCheckConditionDto dailyAlarmCheckCondition;

	public static AlarmCheckConditionByCategoryDto fromDomain(AlarmCheckConditionByCategory domain) {
		int schedule4WCondition = -1;
		DailyAlarmCondition dailyAlarmCondition = new DailyAlarmCondition("", ConExtractedDaily.ALL.value, false,
				Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
		if (domain.getCategory() == AlarmCategory.SCHEDULE_4WEEK && domain.getExtractionCondition() != null) {
			AlarmCheckCondition4W4D schedule4WeekCondition = (AlarmCheckCondition4W4D) domain.getExtractionCondition();
			schedule4WCondition = schedule4WeekCondition.getFourW4DCheckCond().value;
		}
		if (domain.getCategory() == AlarmCategory.DAILY && domain.getExtractionCondition() != null) {
			dailyAlarmCondition = (DailyAlarmCondition) domain.getExtractionCondition();
		}
		return new AlarmCheckConditionByCategoryDto(domain.getCode().v(), domain.getName().v(),
				domain.getCategory().value,
				new AlarmCheckTargetConditionDto(domain.getExtractTargetCondition().isFilterByEmployment(),
						domain.getExtractTargetCondition().isFilterByClassification(),
						domain.getExtractTargetCondition().isFilterByJobTitle(),
						domain.getExtractTargetCondition().isFilterByBusinessType(),
						domain.getExtractTargetCondition().getLstEmploymentCode(),
						domain.getExtractTargetCondition().getLstClassificationCode(),
						domain.getExtractTargetCondition().getLstJobTitleId(),
						domain.getExtractTargetCondition().getLstBusinessTypeCode()),
				domain.getListRoleId(), schedule4WCondition,
				new DailyAlarmCheckConditionDto(dailyAlarmCondition.isAddApplication(),
						dailyAlarmCondition.getConExtractedDaily().value, dailyAlarmCondition.getErrorAlarmCode(),
						dailyAlarmCondition.getExtractConditionWorkRecord(),
						dailyAlarmCondition.getFixedExtractConditionWorkRecord()));
	}

}
