package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class OutputObjectDto {

	private boolean hrEvaluationRefer;
	
	private Integer hrEvaluationDispNumber;
	
	private boolean healthStatusRefer;
	
	private Integer healthStatusDispNumber;
	
	private boolean stressStatusRefer;
	
	private Integer stressStatusDispNumber;
}
