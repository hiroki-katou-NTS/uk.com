package nts.uk.ctx.at.record.dom.workrecord.closurestatus.export;

import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;

/**
 * 休暇残数を計算する締め開始日を取得する
 * @author yuri_tamakoshi
 */
public class GetRemNumClosureStart{

	/**
	 * 休暇残数を計算する締め開始日を取得する
	 * @param employeeId 社員ID
	 * @return 締め開始日(年月日)
	 */
	public static GeneralDate closureDate (String employeeId, CacheCarrier cacheCarrier, Require require){

		// 最新の締め終了日翌日を取得する
		Optional<GeneralDate> closureDate = GetLatestClosureStatusManagement.closureDate(employeeId, require);

		if (closureDate.isPresent()){
			// 受け取った「年月日」を返す
			return closureDate.get();
		}else {
			// 社員に対応する締め開始日を取得する
			val closureStartOpt = GetClosureStartForEmployee.algorithm(require, cacheCarrier, employeeId);
			// 受け取った「締め開始日」を返す
			return closureStartOpt.get();
		}
	}

	public static interface Require extends GetLatestClosureStatusManagement.Require , GetClosureStartForEmployee.RequireM1{

	}
}
