package nts.uk.screen.at.app.command.kdp.kdps01.c;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

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
	private GeneralDate ymd;

	/**
	 * 本人確認状況
	 */
	private Boolean IdentityVerificationStatus;
}
