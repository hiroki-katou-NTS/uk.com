package nts.uk.ctx.sys.auth.pub.grant;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RoleSetGrantedEmployeePub {

	/**
	 * request no. 139: 指定職場の承認者一覧を取得する
	 * @param workplaceId
	 * @param date period
	 * @return List<employee ID>
	 */
	public List<String> findEmpGrantedInWorkplace(String workplaceId, DatePeriod period);
}
