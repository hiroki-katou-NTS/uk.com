package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author dat.lh
 *
 */
@Value
@AllArgsConstructor
public class ErrorStatusOutput {
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
}
