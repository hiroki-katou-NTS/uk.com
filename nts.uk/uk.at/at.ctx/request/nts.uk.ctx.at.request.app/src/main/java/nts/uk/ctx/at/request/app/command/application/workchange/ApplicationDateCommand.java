package nts.uk.ctx.at.request.app.command.application.workchange;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 申請日付
 */
@Value
public class ApplicationDateCommand {

	/**
	 * 開始日
	 */
	private GeneralDate startDate;
	/**
	 * 終了日
	 */
	private GeneralDate endDate;
}
