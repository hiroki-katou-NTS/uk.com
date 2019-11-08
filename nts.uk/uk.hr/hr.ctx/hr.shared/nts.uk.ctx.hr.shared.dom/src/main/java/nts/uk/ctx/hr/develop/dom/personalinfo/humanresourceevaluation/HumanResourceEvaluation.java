package nts.uk.ctx.hr.develop.dom.personalinfo.humanresourceevaluation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;

/**
 * @author anhdt 人事評価管理
 */
@Getter
@Builder
@Stateless
public class HumanResourceEvaluation extends AggregateRoot {

	@Inject
	private HumanResourceEvaluationRepository hrEvalRepo;

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
	public List<PersonnelAssessment> acquisitionPersonnelAssessment(String employeeID) {
		return getPersonnelAssessments().stream().filter(p -> p.getEmployeeID().equalsIgnoreCase(employeeID))
				.collect(Collectors.toList());
	}

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
	public List<PersonnelAssessment> loadHRevaluation(String employeeId, GeneralDate startDate) {
		// メンバー変数「人事評価のリスト」から、人事評価を取得する(Lấy đánh giá nhân sự từ biến số
		// member[List đánh giá nhân sự])
		List<PersonnelAssessment> results = hrEvalRepo.getPersonnelAssessmentByEmployeeId(employeeId);
		if (!CollectionUtil.isEmpty(results)) {
			return results;
		}
		// メンバ変数
		// 「検索済み社員IDリスト」
		// に社員IDが存在するか？(Ở biến số member[SearchedEmployeeIDList] có tồn tại
		// employeeID hay ko?)
		results = acquisitionPersonnelAssessment(employeeId);
		if (!CollectionUtil.isEmpty(results)) {
			return results;
		}
		// 人事評価のロード(Load đánh giá nhân sự)
		HumanResourceEvaluation hrEval = loadHRevaluation(Arrays.asList(employeeId), startDate);
		// メンバー変数「人事評価のリスト」から、人事評価を取得する(từ biến số[List đánh giá nhân sự], lấy
		// đánh giá nhân sự )
		results = hrEval.getPersonnelAssessments();

		return results;
	}

}
