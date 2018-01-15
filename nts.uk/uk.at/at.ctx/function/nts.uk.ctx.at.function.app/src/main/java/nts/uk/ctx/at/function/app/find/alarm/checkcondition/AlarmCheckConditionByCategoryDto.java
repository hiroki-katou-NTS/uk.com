package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;

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

	public static AlarmCheckConditionByCategoryDto fromDomain(AlarmCheckConditionByCategory domain) {
		return new AlarmCheckConditionByCategoryDto(domain.getCode().v(), domain.getName().v(),
				domain.getCategory().value,
				new AlarmCheckTargetConditionDto(domain.getExtractTargetCondition().getId(),
						domain.getExtractTargetCondition().isFilterByEmployment(),
						domain.getExtractTargetCondition().isFilterByClassification(),
						domain.getExtractTargetCondition().isFilterByJobTitle(),
						domain.getExtractTargetCondition().isFilterByBusinessType(),
						domain.getExtractTargetCondition().getLstEmploymentCode(),
						domain.getExtractTargetCondition().getLstClassificationCode(),
						domain.getExtractTargetCondition().getLstJobTitleId(),
						domain.getExtractTargetCondition().getLstBusinessTypeCode()),
				domain.getListRoleId());
	}

}
