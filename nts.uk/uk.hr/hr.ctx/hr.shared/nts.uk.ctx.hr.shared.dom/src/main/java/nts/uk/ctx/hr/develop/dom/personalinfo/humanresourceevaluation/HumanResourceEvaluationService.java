package nts.uk.ctx.hr.develop.dom.personalinfo.humanresourceevaluation;

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
	public HumanResourceEvaluation loadHRevaluation(List<String> employeeIds, GeneralDate startDate,
			HumanResourceEvaluation result) {
		// ドメイン [人事評価] を取得する(lấy domain [đánh giá nhân sự])
		List<PersonnelAssessment> personnelAssessments = hrEvalRepo.getPersonnelAssessmentByEmployeeIds(employeeIds,
				startDate);
		result.fillData(personnelAssessments, employeeIds);
		return result;
	}

	// 人事評価の取得(lay danh gia nhan su)
	public List<PersonnelAssessment> getHRevaluationBySid(String employeeId, GeneralDate startDate,
			HumanResourceEvaluation domain) {
		// メンバー変数「人事評価のリスト」から、人事評価を取得する(Lấy đánh giá nhân sự từ biến số
		// member[List đánh giá nhân sự])
		List<PersonnelAssessment> results = domain.findPersonnelAssessmentBySid(employeeId);
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
		HumanResourceEvaluation hrEval = loadHRevaluation(Arrays.asList(employeeId), startDate, domain);
		// メンバー変数「人事評価のリスト」から、人事評価を取得する(từ biến số[List đánh giá nhân sự], lấy
		// đánh giá nhân sự )
		results = hrEval.getPersonnelAssessments();

		return results;
	}
}
