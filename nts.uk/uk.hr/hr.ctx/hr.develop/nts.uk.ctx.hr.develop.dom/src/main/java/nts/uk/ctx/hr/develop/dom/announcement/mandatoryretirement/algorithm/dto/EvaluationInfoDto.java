package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation.PersonnelAssessment;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryItem;
import nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck.StressCheck;

@AllArgsConstructor
@Getter
public class EvaluationInfoDto {

	//List<人事評価>
	private List<PersonnelAssessment> personnelAssessment;
	
	//List<健康状態>
	private List<MedicalhistoryItem> medicalhistoryItem;
	
	//list<ストレスチェック>
	private List<StressCheck> stressCheck;
}
