package nts.uk.screen.at.app.command.kdp.kdps01.c;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.SelfConfirmDay;

/**
 * 
 * @author sonnlb
 * 
 *         本人確認内容
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfirmDetailCommand {
	/**
	 * 年月日
	 */
	private String ymd;

	/**
	 * 本人確認状況
	 */
	private Boolean identityVerificationStatus;

	public SelfConfirmDay toParam() {

		return new SelfConfirmDay(GeneralDate.fromString(this.ymd, "yyyy/MM/dd"), this.identityVerificationStatus);
	}
}
