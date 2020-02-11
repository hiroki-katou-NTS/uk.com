package nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author anhdt ストレスチェック管理
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StressCheckManagement extends AggregateRoot {

	/**
	 * ストレスチェックのリスト
	 */
	private List<StressCheckResults> stressChecks;
	/**
	 * 検索済み社員IDリスト
	 */
	private List<String> searchedEmployeeIDs;

	public List<StressCheckResults> findStreetCheckBySid(String employeeID) {
		return getStressChecks().stream().filter(p -> p.getEmployeeID().equalsIgnoreCase(employeeID))
				.collect(Collectors.toList());
	}

	public boolean isExistEmployeeId(String employeeId) {
		return searchedEmployeeIDs.contains(employeeId);
	}

}
