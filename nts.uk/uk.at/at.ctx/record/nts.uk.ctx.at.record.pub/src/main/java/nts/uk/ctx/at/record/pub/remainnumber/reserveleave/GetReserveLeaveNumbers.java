package nts.uk.ctx.at.record.pub.remainnumber.reserveleave;

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
}
