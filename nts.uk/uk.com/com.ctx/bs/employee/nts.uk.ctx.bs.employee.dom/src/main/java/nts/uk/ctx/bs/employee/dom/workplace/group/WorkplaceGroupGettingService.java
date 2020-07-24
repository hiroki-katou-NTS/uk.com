package nts.uk.ctx.bs.employee.dom.workplace.group;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.EmployeeAffiliation;

/*
 * DS_社員が所属する職場グループを取得する
 */
public class WorkplaceGroupGettingService {

	public static List<EmployeeAffiliation> get() {
		List<EmployeeAffiliation> affiliations = new ArrayList<>();
		return affiliations;
	}

	public static interface Require {

		// [R-1] 社員が所属している職場を取得する TODO kieu tra ve ko import duoc
		void getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date);

		//[R-2] 職場グループ所属情報を取得する
		List<AffWorkplaceGroup> getWGInfo(List<String> WKPID);
	}

}
