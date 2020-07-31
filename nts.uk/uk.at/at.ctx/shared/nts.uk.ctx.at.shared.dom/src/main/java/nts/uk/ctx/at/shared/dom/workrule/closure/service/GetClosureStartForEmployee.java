package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;

/**
 * 社員に対応する締め開始日を取得する
 * @author shuichi_ishida
 */
public class GetClosureStartForEmployee {

	/**
	 * 社員に対応する締め開始日を取得する
	 * @param employeeId 社員ID
	 * @return 締め開始日
	 */
	public static Optional<GeneralDate> algorithm(RequireM1 require, CacheCarrier cacheCarrier, String employeeId) {
		
		return GetClosureStartForEmployeeProc.algorithm(require, cacheCarrier, employeeId);
	}

	public static interface RequireM1 extends GetClosureStartForEmployeeProc.RequireM1 {
	}
}
