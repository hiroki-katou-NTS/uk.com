package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.app.find.pattern.monthly.setting.Period;

/**
 * 日別確認
 * 
 * @author dat.lh
 */
@AllArgsConstructor
@Value
public class DailyConfirmDto {
	/**
	 * 職場ID
	 */
	private String wkpId;
	/**
	 * 社員ID
	 */
	private String sId;
	/**
	 * 対象日
	 */
	private Period TargetDate;
	/**
	 * 本人確認
	 */
	private boolean personConfirm;
	/**
	 * 上司確認
	 */
	private boolean bossConfirm;
}
