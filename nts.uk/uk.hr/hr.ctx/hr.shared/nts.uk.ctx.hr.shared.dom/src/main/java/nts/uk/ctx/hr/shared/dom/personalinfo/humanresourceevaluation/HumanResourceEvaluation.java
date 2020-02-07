package nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author THanhPV 人事評価管理
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class HumanResourceEvaluation extends AggregateRoot {

	/**
	 * 人事評価のリスト
	 */
	private List<PersonnelAssessmentResults> personnelAssessmentsResult;
	/**
	 * 検索済み社員IDリスト
	 */
	private List<String> employeeIDs;

	public List<PersonnelAssessmentResults> findPersonnelAssessmentBySid(String employeeID) {
		return getPersonnelAssessmentsResult().stream().filter(p -> p.getEmployeeID().equalsIgnoreCase(employeeID))
				.collect(Collectors.toList());
	}
	
	public boolean isExistEmployeeId(String employeeId) {
		return employeeIDs.contains(employeeId);
	}
}
