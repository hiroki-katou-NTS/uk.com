package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

/**
 * 社員の当月の年休付与数を取得する
 * @author shuichu_ishida
 */
public interface GetAnnLeaGrantNumOfCurrentMon {

	/**
	 * 社員の当月の年休付与数を取得する
	 * @param employeeId 社員ID
	 * @return 年休付与数
	 */
	// RequestList281
	AnnLeaGrantNumberExport algorithm(String employeeId);
}
