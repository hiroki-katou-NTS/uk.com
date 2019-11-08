package nts.uk.ctx.hr.develop.dom.humanresourceevaluation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author anhdt 人事評価管理
 */
@Getter
public class HumanResourceEvaluation extends AggregateRoot {
	/**
	 * 人事評価のリスト
	 */
	private List<PersonnelAssessment> personnelAssessments;
	/**
	 * 検索済み社員IDリスト
	 */
	private List<String> employeeIDs;

	/**
	 * @return 人事評価のロード
	 */
	public List<PersonnelAssessment> loadHumanResourceEvaluation() {
		return null;
	}

	/**
	 * 人事評価の取得
	 * 
	 * @param employeeID
	 * @return List<PersonnelAssessment>
	 */
	public List<PersonnelAssessment> acquisitionPersonnelAssessment(String employeeID) {
		return getPersonnelAssessments().stream().filter(p -> p.getEmployeeID().equalsIgnoreCase(employeeID))
				.collect(Collectors.toList());
	}

}
