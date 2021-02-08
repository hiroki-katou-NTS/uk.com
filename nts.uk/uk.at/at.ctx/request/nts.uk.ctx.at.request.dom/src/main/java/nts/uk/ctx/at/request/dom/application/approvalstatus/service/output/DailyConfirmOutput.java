package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * refactor 5
 * 日別確認
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class DailyConfirmOutput {
	/**
	 * 職場ID
	 */
	private String wkpID;
	/**
	 * 社員ID
	 */
	private String empID;
	/**
	 * 対象日
	 */
	private GeneralDate targetDate;
	/**
	 * 本人確認
	 */
	private Boolean personConfirm;
	/**
	 * 上司確認
	 */
	private Integer bossConfirm;
}
