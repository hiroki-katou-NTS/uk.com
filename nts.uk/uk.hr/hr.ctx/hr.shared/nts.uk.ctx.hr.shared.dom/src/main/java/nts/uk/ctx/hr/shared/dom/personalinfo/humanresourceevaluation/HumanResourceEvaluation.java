package nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author anhdt 人事評価管理
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
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
		return personnelAssessments;
	}

	/**
	 * 人事評価の取得
	 * 
	 * @param employeeID
	 * @return List<PersonnelAssessment>
	 */
	public List<PersonnelAssessment> findPersonnelAssessmentBySid(String employeeID) {
		return getPersonnelAssessments().stream().filter(p -> p.getEmployeeID().equalsIgnoreCase(employeeID))
				.collect(Collectors.toList());
	}

	public void fillData(List<PersonnelAssessment> personnelAssessments, List<String> employeeIDs) {
		// // メンバー変数「人事評価のリスト」へ、List<人事評価>を追加する(Thêm List<Đánh giá nhân sự>
		// vào
		// biến số member[List đánh giá nhân sự])
		this.employeeIDs = employeeIDs;
		this.personnelAssessments = personnelAssessments;
	}
	
	public boolean isExistEmployeeId(String employeeId) {
		return employeeIDs.contains(employeeId);
	}
}
