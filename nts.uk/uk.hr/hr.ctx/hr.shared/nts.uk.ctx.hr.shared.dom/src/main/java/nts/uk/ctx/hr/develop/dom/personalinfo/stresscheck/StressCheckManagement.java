package nts.uk.ctx.hr.develop.dom.personalinfo.stresscheck;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;

/**
 * @author anhdt ストレスチェック履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Stateless
public class StressCheckManagement extends AggregateRoot {

	@Inject
	private StressCheckRepository stressCheckRepo;
	/**
	 * ストレスチェックのリスト
	 */
	private List<StressCheck> stressChecks;
	/**
	 * 検索済み社員IDリスト
	 */
	private List<String> searchedEmployeeIDs;

	/**
	 * 人事評価の取得
	 * 
	 * @param employeeID
	 * @return List<PersonnelAssessment>
	 */
	public List<StressCheck> acquisitionStreetCheck(String employeeID) {
		return getStressChecks().stream().filter(p -> p.getEmployeeID().equalsIgnoreCase(employeeID))
				.collect(Collectors.toList());
	}

	// ストレスチェックのロード
	public StressCheckManagement loadStressCheck(List<String> employeeIds, GeneralDate startDate) {
		// ドメイン [ストレスチェック] を取得する(Lấy domain [check stress])
		List<StressCheck> stressChecks = stressCheckRepo.getPersonnelAssessmentByEmployeeIds(employeeIds, startDate);

		StressCheckManagement result = StressCheckManagement.builder()
				// メンバー変数「ストレスチェックのリスト」へ、List<ストレスチェック>を追加する(Thêm List<check
				// stress> vào biến số member [List check stress])
				.stressChecks(stressChecks)
				// メンバー変数「検索済み社員IDリスト」へList<社員ID>（input）を追加する(Thêm
				// List<EmployeeID>（input）) vào biến số [SearchedEmployeeIDList]
				.searchedEmployeeIDs(employeeIds).build();

		return result;
	}

	// ストレスチェックの取得
	public List<StressCheck> loadStressCheck(String employeeId, GeneralDate startDate) {
		// メンバー変数「ストレスチェックのリスト」から、ストレスチェックを取得する(từ biến số member[List check
		// stress] lấy CheckStress)
		List<StressCheck> results = stressCheckRepo.getPersonnelAssessmentByEmployeeId(employeeId);
		if (!CollectionUtil.isEmpty(results)) {
			return results;
		}
		// メンバ変数
		// 「ロード済み社員IDリスト」
		// に社員IDが存在するか？(Ở biến số member[LoadedEmployeeIDList] có tồn tại
		// EmployeeID ko?)
		results = acquisitionStreetCheck(employeeId);
		if (!CollectionUtil.isEmpty(results)) {
			return results;
		}
		// ストレスチェックのロード(Load CheckStress)
		StressCheckManagement hrEval = loadStressCheck(Arrays.asList(employeeId), startDate);
		// メンバー変数「外国人在留履歴情報のリスト」から、外国人在留履歴情報を取得する(Từ biến số member[List thông
		// tin lịch sử cư trú của người nước ngoài], lấy thông tin lịch sử cư
		// trú của người nước ngoài)
		results = hrEval.getStressChecks();

		return results;
	}
}
