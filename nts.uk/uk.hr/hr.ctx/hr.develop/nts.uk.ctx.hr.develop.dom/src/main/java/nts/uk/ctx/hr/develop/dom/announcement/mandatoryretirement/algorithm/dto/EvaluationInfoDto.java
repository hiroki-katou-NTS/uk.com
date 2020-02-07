package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EvaluationInfoDto {

	//List<人事評価>
	private List<ComprehensiveEvaluationDto> hrEvaluationList;
	
	//List<健康状態>
	private List<ComprehensiveEvaluationDto> healthStatusList;
	
	//list<ストレスチェック>
	private List<ComprehensiveEvaluationDto> stressStatusList;
}
