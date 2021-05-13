package nts.uk.ctx.at.record.dom.workrecord.closurestatus.export;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;

/**
 * 最新の締め終了日翌日を取得する
 * @author yuri_tamakoshi
 */
public class GetLatestClosureStatusManagement {

	/**
	 * 最新の締め終了日翌日を取得する
	 * @param employeeId 社員ID
	 * @return 年月日
	 */
	public static Optional<GeneralDate> closureDate (String employeeId, Require require) {

		// 「締め状態管理」を取得する
		Optional<ClosureStatusManagement> sttMng = require.latestClosureStatusManagement(employeeId);

		// 最新の締め終了日翌日を取得する
		return sttMng.map(c -> c.getPeriod().end().addDays(1));
	}

	public static interface Require {
		// 締め状態管理を取得する（社員ID）
		Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId);
	}
}
