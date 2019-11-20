package nts.uk.ctx.hr.develop.dom.personalinfo.stresscheck;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;

@Stateless
public class StressCheckService {

	@Inject
	private StressCheckRepository stressCheckRepo;

	// ストレスチェックのロード
	public StressCheckManagement loadStressCheck(List<String> employeeIds, GeneralDate startDate,
			StressCheckManagement result) {
		// ドメイン [ストレスチェック] を取得する(Lấy domain [check stress])
		List<StressCheck> stressChecks = stressCheckRepo.getPersonnelAssessmentByEmployeeIds(employeeIds, startDate);

		result.fillData(stressChecks, employeeIds);

		return result;
	}

	// ストレスチェックの取得
	public List<StressCheck> getStressCheck(String employeeId, GeneralDate startDate,
			StressCheckManagement stressCheck) {
		// メンバー変数「ストレスチェックのリスト」から、ストレスチェックを取得する(từ biến số member[List check
		// stress] lấy CheckStress)
		List<StressCheck> results = stressCheck.findStreetCheckBySid(employeeId);
		if (!CollectionUtil.isEmpty(results)) {
			return results;
		}
		// メンバ変数
		// 「ロード済み社員IDリスト」
		// に社員IDが存在するか？(Ở biến số member[LoadedEmployeeIDList] có tồn tại
		// EmployeeID ko?)
		if (stressCheck.isExistEmployeeId(employeeId)) {
			return results;
		}
		// ストレスチェックのロード(Load CheckStress)
		StressCheckManagement hrEval = loadStressCheck(Arrays.asList(employeeId), startDate, stressCheck);
		// メンバー変数「外国人在留履歴情報のリスト」から、外国人在留履歴情報を取得する(Từ biến số member[List thông
		// tin lịch sử cư trú của người nước ngoài], lấy thông tin lịch sử cư
		// trú của người nước ngoài)
		results = hrEval.getStressChecks();

		return results;
	}
}
