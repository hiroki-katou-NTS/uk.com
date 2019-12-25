package nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;

@Stateless
public class HumanResourceEvaluationService {
	@Inject
	private HumanResourceEvaluationRepository hrEvalRepo;

	// 人事評価のロード
	public HumanResourceEvaluation loadHRevaluation(List<String> employeeIds, GeneralDate startDate) {
		List<PersonnelAssessmentResults> personnelAssessmentsResult = hrEvalRepo.getPersonnelAssessmentByEmployeeIds(employeeIds, startDate);
		return new HumanResourceEvaluation(personnelAssessmentsResult, employeeIds);
	}

	// 人事評価の取得(lay danh gia nhan su)
	public List<PersonnelAssessmentResults> getHRevaluationBySid(String employeeId, GeneralDate startDate,
			HumanResourceEvaluation domain) {
		// メンバー変数「人事評価のリスト」から、人事評価を取得する(Lấy đánh giá nhân sự từ biến số
		// member[List đánh giá nhân sự])
		List<PersonnelAssessmentResults> results = domain.findPersonnelAssessmentBySid(employeeId);
		if (!CollectionUtil.isEmpty(results)) {
			return results;
		}
		// メンバ変数
		// 「検索済み社員IDリスト」
		// に社員IDが存在するか？(Ở biến số member[SearchedEmployeeIDList] có tồn tại
		// employeeID hay ko?)
		if (domain.isExistEmployeeId(employeeId)) {
			return results;
		}
		// 人事評価のロード(Load đánh giá nhân sự)
		HumanResourceEvaluation hrEval = loadHRevaluation(Arrays.asList(employeeId), startDate);
		// メンバー変数「人事評価のリスト」から、人事評価を取得する(từ biến số[List đánh giá nhân sự], lấy
		// đánh giá nhân sự )
		results = hrEval.getPersonnelAssessmentsResult();

		return results;
	}
}
