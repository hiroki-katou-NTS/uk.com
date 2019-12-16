package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EvaluationInformationDto {

	//List<人事評価>
	private List<HealthStatusDto> hrEvaluation;
	
	//List<健康状態>
	private List<HealthStatusDto> healthStatus;
	
	//List<ストレス状態>
	private List<HealthStatusDto> stressStatus;
}
