package nts.uk.ctx.bs.employee.pub.department.aff;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AffDepartmentPub {

	/**
	 * [RQ643]社員ID(List)と基準日から部門IDを取得する
	 * @param sids
	 * @param baseDate
	 */
	public List<RequestList643Export> getAffDeptHistByEmpIdAndBaseDate(List<String> sids, GeneralDate baseDate);


}
