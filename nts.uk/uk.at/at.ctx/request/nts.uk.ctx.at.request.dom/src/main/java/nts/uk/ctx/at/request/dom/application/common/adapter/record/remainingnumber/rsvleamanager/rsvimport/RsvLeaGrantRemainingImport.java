package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class RsvLeaGrantRemainingImport {
	/** 付与日1 */
	private String grantDate;
	/** 期限日 5*/
	private String deadline;
	/** 付与日数2 */
	private Double grantNumber;
	/** 使用数 3*/
	private Double usedNumber;
	/** 残日数4 */
	private Double remainingNumber;
}
