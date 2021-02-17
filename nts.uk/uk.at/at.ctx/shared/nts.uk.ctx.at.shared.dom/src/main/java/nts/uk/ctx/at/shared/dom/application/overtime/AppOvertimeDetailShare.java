package nts.uk.ctx.at.shared.dom.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

/**
 * 時間外時間の詳細
 */
@AllArgsConstructor
@Getter
public class AppOvertimeDetailShare {

	/**
	 * 会社ID
	 */
	@Setter
	private String cid;

	/**
	 * 申請ID
	 */
	@Setter
	private String appId;

	/**
	 * 年月
	 */
	@Setter
	private YearMonth yearMonth;

	/**
	 * 36協定時間
	 */
	@Setter
	private Time36AgreeShare time36Agree;

	/**
	 * 36協定上限時間
	 */
	@Setter
	private Time36AgreeUpperLimitShare time36AgreeUpperLimit;

}
