package nts.uk.ctx.at.record.app.find.application.realitystatus;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author dat.lh
 *
 */
@Value
@AllArgsConstructor
public class DailyConfirmDto {
	/**
	 * 対象日
	 */
	private GeneralDate targetDate;
	/**
	 * 本人確認
	 */
	private boolean personConfirm;
	/**
	 * 上司確認
	 */
	private boolean bossConfirm;
}
