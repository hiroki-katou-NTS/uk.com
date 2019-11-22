package nts.uk.ctx.hr.develop.dom.personalinfo.stresscheck;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author anhdt ストレスチェック管理
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StressCheckManagement extends AggregateRoot {

	/**
	 * ストレスチェックのリスト
	 */
	private List<StressCheck> stressChecks;
	/**
	 * 検索済み社員IDリスト
	 */
	private List<String> searchedEmployeeIDs;

	public List<StressCheck> findStreetCheckBySid(String employeeID) {
		return getStressChecks().stream().filter(p -> p.getEmployeeID().equalsIgnoreCase(employeeID))
				.collect(Collectors.toList());
	}

	public boolean isExistEmployeeId(String employeeId) {
		return searchedEmployeeIDs.contains(employeeId);
	}

	public void fillData(List<StressCheck> stressChecks, List<String> searchedEmployeeIDs) {
		// メンバー変数「ストレスチェックのリスト」へ、List<ストレスチェック>を追加する(Thêm List<check
		// stress> vào biến số member [List check stress])
		this.stressChecks = stressChecks;
		// メンバー変数「検索済み社員IDリスト」へList<社員ID>（input）を追加する(Thêm
		// List<EmployeeID>（input）) vào biến số [SearchedEmployeeIDList]
		this.searchedEmployeeIDs = searchedEmployeeIDs;
	}

}
