package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployeeImpl.Require;

/**
 * 社員に対応する締め開始日を取得する
 * @author shuichi_ishida
 */
public interface GetClosureStartForEmployee {

	/**
	 * 社員に対応する締め開始日を取得する
	 * @param employeeId 社員ID
	 * @return 締め開始日
	 */
	Optional<GeneralDate> algorithm(String employeeId);
	Optional<GeneralDate> algorithmRequire(Require require, CacheCarrier cacheCarrier, String employeeId);
}
