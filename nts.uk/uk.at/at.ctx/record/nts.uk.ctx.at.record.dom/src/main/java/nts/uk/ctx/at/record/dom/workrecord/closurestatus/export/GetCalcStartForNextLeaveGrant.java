package nts.uk.ctx.at.record.dom.workrecord.closurestatus.export;

import nts.arc.time.GeneralDate;

/**
 * 次回年休付与を計算する開始日を取得する
 * @author shuichi_ishida
 */
public interface GetCalcStartForNextLeaveGrant {

	/**
	 * 次回年休付与を計算する開始日を取得する
	 * @param employeeId 社員ID
	 * @return 年月日
	 */
	GeneralDate algorithm(String employeeId);
}
