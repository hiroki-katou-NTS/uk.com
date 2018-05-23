package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 日別確認
 * 
 * @author dat.lh
 */
@AllArgsConstructor
@Getter
@Setter
public class DailyConfirmOutput {
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
