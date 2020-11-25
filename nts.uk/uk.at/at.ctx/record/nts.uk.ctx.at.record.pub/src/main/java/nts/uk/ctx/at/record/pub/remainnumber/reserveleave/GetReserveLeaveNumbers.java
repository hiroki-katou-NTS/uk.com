package nts.uk.ctx.at.record.pub.remainnumber.reserveleave;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;

/**
 * 社員の積立年休の月初残・使用・残数・未消化を取得する
 * @author shuichu_ishida
 */
public interface GetReserveLeaveNumbers {

	/**
	 * 社員の積立年休の月初残・使用・残数・未消化を取得する
	 * @param employeeId 社員ID
	 * @return 積立年休現在状況
	 */
	// RequestList268
	ReserveLeaveNowExport algorithm(String employeeId);
	/**
	 * @author hoatt
	 * 社員の積立年休の月初残・使用・残数・未消化を取得する
	 * RequestList268 ver2
	 * @param employeeId 社員ID
	 * @return 積立年休現在状況
	 */
	public ReserveLeaveNowExport getRsvRemainVer2(String employeeId, Optional<GeneralDate> closureDate, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets);
}
