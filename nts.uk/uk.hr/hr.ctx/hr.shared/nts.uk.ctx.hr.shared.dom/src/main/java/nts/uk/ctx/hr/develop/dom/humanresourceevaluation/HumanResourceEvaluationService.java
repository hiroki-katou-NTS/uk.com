package nts.uk.ctx.hr.develop.dom.humanresourceevaluation;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;

/**
 * @author anhdt
 * 
 */
@Stateless
public class HumanResourceEvaluationService {

	@Inject
	private HumanResourceEvaluationRepository hrEvalRepo;

	// 人事評価のロード
	public HumanResourceEvaluation loadHRevaluation(List<String> employeeIds, GeneralDate startDate) {
		// ドメイン [人事評価] を取得する(lấy domain [đánh giá nhân sự])
		List<PersonnelAssessment> personnelAssessments = hrEvalRepo.getPersonnelAssessmentByEmployeeIds(employeeIds,
				startDate);

		HumanResourceEvaluation result = HumanResourceEvaluation.builder()
				// メンバー変数「人事評価のリスト」へ、List<人事評価>を追加する(Thêm List<Đánh giá nhân sự>
				// vào
				// biến số member[List đánh giá nhân sự])
				.personnelAssessments(personnelAssessments)
				// メンバー変数「検索済み社員IDリスト」へList<社員ID>（input）を追加する(Thêm
				// list<EmployeeID> vào biến số member[SearchedEmployeeIDList])
				.employeeIDs(employeeIds).build();

		return result;
	}

	// 人事評価の取得(lay danh gia nhan su)
	public List<PersonnelAssessment> loadHRevaluation(String employeeIds, GeneralDate startDate) {
		
		return null;
	}
}
