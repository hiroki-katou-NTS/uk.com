package nts.uk.ctx.bs.employee.dom.workplace.group;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.EmployeeAffiliation;

/*
 * 社員が所属する職場グループを取得する
 */
public class WorkplaceGroupGettingService {

	/**
	 * 取得する
	 * @param require Require
	 * @param baseDate 基準日
	 * @param employeeID
	 * @return
	 */
	public static List<EmployeeAffiliation> get(Require require, GeneralDate baseDate, List<String> employeeIDs) {

		// 社員が所属している職場を取得する
		Map<String, String> empAffiliations = employeeIDs.stream()
				.collect(Collectors.toMap( e -> e, e -> require.getAffWkpHistItemByEmpDate(e, baseDate) ))
			.entrySet().stream()
				.filter( e -> e.getValue().isPresent() )
				.collect(Collectors.toMap( Map.Entry::getKey, e -> e.getValue().get() ));


		// 職場が所属する職場グループを取得する
		List<String> wkpIDs = empAffiliations.values().stream().distinct().collect(Collectors.toList());
		List<AffWorkplaceGroup> affWorkplaceGroups = require.getWGInfo(wkpIDs);


		// 社員の所属組織を返す
		return employeeIDs.stream()
				.map( e -> WorkplaceGroupGettingService.create( e, empAffiliations, affWorkplaceGroups ) )
				.collect(Collectors.toList());

	}



	/**
	 * 社員の所属組織を作成する
	 * @param employeeID 社員ID
	 * @param affEmpWorkplaces 所属職場リスト
	 * @param affWorkplaceGroups 職場グループ所属情報リスト
	 * @return
	 */
	private static EmployeeAffiliation create(
			String employeeID, Map<String, String> affEmpWorkplaces, List<AffWorkplaceGroup> affWorkplaceGroups
	) {

		String wkpIDs = affEmpWorkplaces.get(employeeID);
		Optional<AffWorkplaceGroup> affWorkplaceGroup = affWorkplaceGroups.stream()
				.filter( i -> i.getWorkplaceId().equals(wkpIDs) ).findFirst();

		if (!affWorkplaceGroup.isPresent()) {
			// 職場グループに所属していない
			return EmployeeAffiliation.createWithoutInfoAndWG(employeeID, wkpIDs);
		} else {
			// 職場グループに所属している
			return EmployeeAffiliation.createWithoutInfo(employeeID, wkpIDs, affWorkplaceGroup.get().getWorkplaceGroupId());
		}

	}





	public static interface Require {

		// 社員が所属している職場を取得する
		Optional<String> getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date);

		// 職場グループ所属情報を取得する
		List<AffWorkplaceGroup> getWGInfo(List<String> wkpId);

	}

}
