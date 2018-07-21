package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
@Getter
@AllArgsConstructor
public class RsvLeaGrantRemainingImport {
	/** 付与日 */
	private GeneralDate grantDate;
	/** 期限日 */
	private GeneralDate deadline;
	/** 付与日数 */
	private Double grantNumber;
	/** 使用数 */
	private Double usedNumber;
	/** 残日数 */
	private Double remainingNumber;
}
